package lis.co.edu.udea.lectorrfid.view.activity


import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import lis.co.edu.udea.lectorrfid.R
import lis.co.edu.udea.lectorrfid.`interface`.IViewMain
import lis.co.edu.udea.lectorrfid.presenter.MainPresenter

class MainActivity : BaseActivity(), IViewMain {

    private lateinit var bSend: Button
    private lateinit var frameCamera: FrameLayout
    private lateinit var iViewPreview: ImageView
    private lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createSnackBar(mainActivity_layout_container)
        initViews()
        initListeners()
    }

    private fun initViews() {
        mainPresenter = MainPresenter(this)
        mainPresenter.initCameraController()
        this.bSend = mainActivity_button_send
        this.frameCamera = mainActivity_layout_cameraContainer
        this.iViewPreview = mainActivity_image_picturePreview

    }

    private fun initListeners() {

        bSend.setOnClickListener {
            run {
                val toast = Toast.makeText(applicationContext, getString(R.string.mainActivity_string_messageEmptyPicture), Toast.LENGTH_SHORT)
                toast.show()
            }
        }
        frameCamera.setOnClickListener {
            takePicture()
            Toast.makeText(applicationContext, R.string.mainActivity_string_messageTakePicture, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showPictureError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun takePicture() {
        mainPresenter.takePicture()
    }

    override fun showPreview(photo: Bitmap?) {
//        runOnUiThread { Glide.with(this).load(photo).into(iViewPreview) }
          iViewPreview.setImageBitmap(photo)
    }

}
