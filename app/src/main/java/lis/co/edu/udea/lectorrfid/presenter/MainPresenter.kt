package lis.co.edu.udea.lectorrfid.presenter

import lis.co.edu.udea.lectorrfid.model.CameraController
import lis.co.edu.udea.lectorrfid.view.activity.BaseActivity

class MainPresenter(activity: BaseActivity){

    private val cameraController:CameraController = CameraController(activity)

    fun initCameraController(){
        cameraController.init()
    }


}