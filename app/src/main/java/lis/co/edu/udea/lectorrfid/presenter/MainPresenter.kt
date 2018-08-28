package lis.co.edu.udea.lectorrfid.presenter

import android.graphics.Bitmap
import android.net.Uri
import lis.co.edu.udea.lectorrfid.`interface`.IViewMain
import lis.co.edu.udea.lectorrfid.model.CameraController
import lis.co.edu.udea.lectorrfid.view.activity.MainActivity

class MainPresenter(activity: MainActivity) {

    private val cameraController: CameraController = CameraController(activity)
    private var view: IViewMain = activity

    fun initCameraController() {
        cameraController.init(this)
    }

    fun showPreview(photo:Uri){
        view.showPreview(photo)
    }

    fun takePicture() {
        if (!cameraController.takePicture()) view.showPictureError()
    }

    fun showWaitPicture(){
        view.showWaitPicture()
    }

    fun updateProgress(){
        view.updateProgressDialog()
    }

    fun dismissProgressDialog(){
        view.dismissProgressDialog()
    }


}