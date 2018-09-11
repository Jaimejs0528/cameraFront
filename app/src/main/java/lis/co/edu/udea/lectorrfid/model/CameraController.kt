package lis.co.edu.udea.lectorrfid.model

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.hardware.Camera
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.android.synthetic.main.activity_main.*
import lis.co.edu.udea.lectorrfid.presenter.MainPresenter
import lis.co.edu.udea.lectorrfid.util.Tool
import lis.co.edu.udea.lectorrfid.view.activity.BaseActivity
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

open class CameraController(private val activity: BaseActivity) : SurfaceHolder.Callback {

    private lateinit var mSurfaceView: SurfaceView
    private lateinit var mSurfaceHolder: SurfaceHolder
    private var mCamera: Camera? = null
    private var preview = false
    private lateinit var mPresenter: MainPresenter

    fun init(presenter: MainPresenter) {
        activity.window.setFormat(PixelFormat.UNKNOWN)
        mSurfaceView = activity.mainActivity_surfaceView_cameraPreview
        mSurfaceHolder = mSurfaceView.holder
        mSurfaceHolder.addCallback(this)
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        mPresenter = presenter
    }

    fun takePicture(): Boolean {
        if (preview) {
            val pictureCallBack: Camera.PictureCallback = object : Camera.PictureCallback {
                override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
                    val task = ASyncTakePicture(mPresenter)
                    task.execute(data)
                    surfaceDestroyed(mSurfaceHolder)
                }
            }
            mCamera?.takePicture(null, null, pictureCallBack)
            return true
        } else {
            Log.d("camera", "no tomo fotos")
        }
        return false
    }

    fun destroyPreview(){
        surfaceDestroyed(mSurfaceHolder)
    }

    fun initPreviewCamera() {
        if (mCamera == null) {
            ASyncCam().execute(mSurfaceHolder)
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        mCamera?.stopPreview()
        mCamera?.setPreviewCallback(null)
        mCamera?.release()
        mCamera = null
        preview = false
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        if (Tool.hasPermission(Manifest.permission.CAMERA, activity) &&
                Tool.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, activity)) {
            ASyncCam().execute(mSurfaceHolder)
        } else {
            Tool.makeRequest(arrayOf(Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    Tool.camera.CAMERA_PERMISSION, activity)
        }
    }


    @SuppressLint("StaticFieldLeak")
    inner class ASyncCam : AsyncTask<SurfaceHolder, Void, Camera>() {
        override fun doInBackground(vararg surface: SurfaceHolder): Camera? {
            val camera = Camera.open()
            try {
                camera.setDisplayOrientation(90)
                val params: Camera.Parameters? = camera.parameters
                params?.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
                params?.sceneMode = Camera.Parameters.SCENE_MODE_PORTRAIT
                params?.whiteBalance = Camera.Parameters.WHITE_BALANCE_AUTO
                camera.parameters = params
                camera?.setPreviewDisplay(surface[0])
                camera.startPreview()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return camera
        }

        override fun onPostExecute(result: Camera?) {
            super.onPostExecute(result)
            mCamera = result
            mPresenter.hidePreview()
            mPresenter.keepScreeOn()
            preview = true
        }
    }

    class ASyncTakePicture(var presenter: MainPresenter) : AsyncTask<ByteArray, Void, File>() {
        override fun onPreExecute() {
            super.onPreExecute()
            presenter.showWaitPicture()
        }

        override fun onPostExecute(result: File?) {
            super.onPostExecute(result)
            presenter.showPreview(Uri.fromFile(result))
            presenter.dismissProgressDialog()
        }

        override fun doInBackground(vararg data: ByteArray): File {
            var image: FileOutputStream? = null
            val imageName = "${Calendar.getInstance().timeInMillis}${Tool.camera.JPG_IMAGE}"
            val file = File(Environment.getExternalStorageDirectory(), imageName)
            try {
                image = FileOutputStream(file)
                val options: BitmapFactory.Options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.RGB_565
                var picture: Bitmap = BitmapFactory.decodeByteArray(data[0], 0, data[0].size,options)
                picture = Tool.rotateBitmap(picture, 90f)
                picture.compress(Bitmap.CompressFormat.JPEG, 100, image)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                image?.close()
            }
            return file
        }
    }

}