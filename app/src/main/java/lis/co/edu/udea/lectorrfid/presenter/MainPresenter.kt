package lis.co.edu.udea.lectorrfid.presenter

import lis.co.edu.udea.lectorrfid.`interface`.IViewMain
import lis.co.edu.udea.lectorrfid.model.CameraController
import lis.co.edu.udea.lectorrfid.view.activity.BaseActivity

class MainPresenter(activity: BaseActivity){

    private val cameraController:CameraController = CameraController(activity)
    private lateinit var view:IViewMain

    fun initCameraController(){
        cameraController.init()
    }

    fun takePicture(){
        if (!cameraController.takePicture())  view.showPictureError()
    }


}