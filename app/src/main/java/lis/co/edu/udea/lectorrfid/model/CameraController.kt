package lis.co.edu.udea.lectorrfid.model

import android.Manifest
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.hardware.Camera
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.android.synthetic.main.activity_main.*
import lis.co.edu.udea.lectorrfid.R
import lis.co.edu.udea.lectorrfid.presenter.MainPresenter
import lis.co.edu.udea.lectorrfid.util.Tool
import lis.co.edu.udea.lectorrfid.view.activity.BaseActivity
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class CameraController(private val activity: BaseActivity) : SurfaceHolder.Callback {

    private lateinit var mSurfaceView: SurfaceView
    private lateinit var mSurfaceHolder: SurfaceHolder
    private var mCamera: Camera? = null
    private var preview = false
    private lateinit var mPresenter:MainPresenter

    fun init(presenter: MainPresenter) {
        activity.window.setFormat(PixelFormat.UNKNOWN)
        mSurfaceView = activity.mainActivity_surfaceView_cameraPreview
        mSurfaceHolder = mSurfaceView.holder
        mSurfaceHolder.addCallback(this)
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        mPresenter = presenter
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        if (preview) {
            mCamera?.stopPreview()
            preview = false
        }

        if (mCamera != null) {
            try {
                mCamera?.setPreviewDisplay(mSurfaceHolder)
                mCamera?.startPreview()
                preview = true
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        mCamera?.stopPreview()
        mCamera?.release()
        mCamera = null
        preview = false
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        if (Tool.hasPermission(Manifest.permission.CAMERA, activity) &&
                Tool.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, activity)) {
            mCamera = asyncCam(mCamera).execute().get()
        } else {
            Tool.makeRequest(Manifest.permission.CAMERA, Tool.camera.CAMERA_PERMISSION, activity)
            Tool.makeRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE, Tool.camera.CAMERA_PERMISSION, activity)
        }
    }

    class asyncCam(var camera: Camera?) : AsyncTask<Void, Void, Camera>() {
        override fun doInBackground(vararg p0: Void?): Camera? {
            camera = Camera.open()
            camera?.setDisplayOrientation(90)
            val parms: Camera.Parameters? = this.camera?.parameters
            parms?.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
            camera?.parameters = parms
            return camera
        }

    }

    class asyncTakePicture(var camera: Camera?,var presenter: MainPresenter): AsyncTask<Void, Void, Bitmap>() {

        override fun onPostExecute(result: Bitmap) {
            super.onPostExecute(result)
            presenter.showPreview(result)
            Log.d("hola","cam!")

        }

        override fun doInBackground(vararg p0: Void?): Bitmap {
            val conf = Bitmap.Config.ARGB_8888 // see other conf types
            val bmp = Bitmap.createBitmap(400, 400, conf)
//            BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.ic_error_404)
            var picture: Bitmap =bmp
            val pictureCallBack: Camera.PictureCallback = object : Camera.PictureCallback {
                override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
                    var image: FileOutputStream? = null
                    val file: File
                    try {
                        val imageName = "${Calendar.getInstance().timeInMillis}.jpg"
                        file = File(Environment.getExternalStorageDirectory(), imageName)
                        image = FileOutputStream(file)
                        picture = BitmapFactory.decodeByteArray(data, 0, data?.size!!)
                        picture = Tool.rotateBitmap(picture, 90f)
                        picture.compress(Bitmap.CompressFormat.PNG, 100, image)

                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        image?.close()
                    }

                }

            }
            camera?.takePicture(null, null, pictureCallBack)
            return picture
        }

    }

    fun takePicture(): Boolean {
        asyncTakePicture(mCamera,mPresenter).execute()
        return true
    }

}