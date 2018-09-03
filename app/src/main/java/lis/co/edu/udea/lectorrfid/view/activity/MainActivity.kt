package lis.co.edu.udea.lectorrfid.view.activity


import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import lis.co.edu.udea.lectorrfid.R
import lis.co.edu.udea.lectorrfid.`interface`.IViewMain
import lis.co.edu.udea.lectorrfid.presenter.MainPresenter
import lis.co.edu.udea.lectorrfid.util.Tool

class MainActivity : BaseActivity(), IViewMain {
    private lateinit var bSend: Button
    private lateinit var frameCamera: FrameLayout
    private lateinit var iViewPreview: ImageView
    private lateinit var deleteButton: ImageView
    private lateinit var mainPresenter: MainPresenter
    private var mUriPhoto: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews(savedInstanceState)
        initListeners()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putString(Tool.camera.URI_STATE, mUriPhoto?.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
            val uriString:String? = savedInstanceState?.getString(Tool.camera.URI_STATE)?:"null"
            Log.d("uri", uriString ?: "null")
            mUriPhoto = Uri.parse(uriString)
    }

    private fun initViews(savedInstanceState: Bundle?) {
        mainPresenter = MainPresenter(this)
        mainPresenter.initCameraController()
        createSnackBar(mainActivity_layout_container)
        this.bSend = mainActivity_button_send
        this.frameCamera = mainActivity_layout_cameraContainer
        this.iViewPreview = mainActivity_image_picturePreview
        this.deleteButton = mainActivity_button_delete
        val uriString:String? = savedInstanceState?.getString(Tool.camera.URI_STATE)?:"null"
        Log.d("uri", uriString ?: "null")
        if (mUriPhoto != null) {
            Glide.with(this).load(mUriPhoto).into(iViewPreview)
            iViewPreview.visibility = View.VISIBLE
        }

    }

    private fun initListeners() {

        bSend.setOnClickListener {
            run {
                val toast = Toast.makeText(applicationContext, getString(R.string.mainActivity_string_messageEmptyPicture), Toast.LENGTH_SHORT)
                mainPresenter.initPreviewCamera()
                toast.show()
            }
        }
        deleteButton.setOnClickListener {
            run {
                mainPresenter.deleteImage(mUriPhoto)
                mainPresenter.initPreviewCamera()
            }
        }
        frameCamera.setOnClickListener {
            takePicture()
            keepScreenOn(false)
        }
    }

    override fun takePicture() {
        mainPresenter.takePicture()
    }

    override fun showPreview(photo: Uri) {
        Log.d("hola", "cam! ${photo}")
        mUriPhoto = photo
        Glide.with(this).load(photo).into(iViewPreview)
        iViewPreview.visibility = View.VISIBLE
        deleteButton.visibility = View.VISIBLE

    }

    override fun showWaitPicture() {
        createProgressDialog()
        showProgressDialog(R.string.mainActivity_string_messageChargeImage, ProgressDialog.STYLE_SPINNER)

    }

    override fun hidePreview() {
        iViewPreview.visibility = View.INVISIBLE
        deleteButton.visibility = View.GONE
    }

    override fun dismissProgressDialog() {
        hideProgressDialog()
    }

    override fun showToastMessage(message: Int) {
        showToast(message)
    }

    override fun keepScreenOn(isNeeded: Boolean) {
        if (isNeeded)
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        else
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}
