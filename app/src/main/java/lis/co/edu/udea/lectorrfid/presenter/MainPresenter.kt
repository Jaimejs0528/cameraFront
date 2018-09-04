package lis.co.edu.udea.lectorrfid.presenter

import android.net.Uri
import lis.co.edu.udea.lectorrfid.R
import lis.co.edu.udea.lectorrfid.`interface`.IViewMain
import lis.co.edu.udea.lectorrfid.model.CameraController
import lis.co.edu.udea.lectorrfid.util.Tool
import lis.co.edu.udea.lectorrfid.view.activity.MainActivity
import java.io.File

class MainPresenter(activity: MainActivity) {

    private val cameraController: CameraController = CameraController(activity)
    private var view: IViewMain = activity

    fun initCameraController() {
        cameraController.init(this)
    }

    fun initPreviewCamera() {
        cameraController.initPreviewCamera()
    }

    fun showPreview(photo: Uri) {
        view.showPreview(photo)
    }

    fun takePicture() {
        !cameraController.takePicture()
    }

    fun showWaitPicture() {
        view.showWaitPicture()
    }

    fun hidePreview() {
        view.hidePreview()
    }

    fun dismissProgressDialog() {
        view.dismissProgressDialog()
    }

    fun keepScreeOn(isNeeded: Boolean = true) {
        view.keepScreenOn(isNeeded)
    }

    fun deleteImage(photo: File) {
        if (Tool.deletePhotoFile(photo))
            view.showToastMessage(R.string.mainActivity_string_messageSuccessDelete)
        else
            view.showToastMessage(R.string.mainActivity_string_messageErrorDelete)
    }
}