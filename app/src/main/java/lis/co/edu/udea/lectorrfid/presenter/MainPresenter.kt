package lis.co.edu.udea.lectorrfid.presenter

import android.graphics.Bitmap
import lis.co.edu.udea.lectorrfid.`interface`.IViewMain
import lis.co.edu.udea.lectorrfid.model.CameraController
import lis.co.edu.udea.lectorrfid.view.activity.MainActivity

class MainPresenter(activity: MainActivity) {

    private val cameraController: CameraController = CameraController(activity)
    private var view: IViewMain = activity

    fun initCameraController() {
        cameraController.init(this)
    }

    fun showPreview(photo:Bitmap){
        view.showPreview(photo)
    }

    fun takePicture() {
        if (!cameraController.takePicture()) view.showPictureError()
    }


}