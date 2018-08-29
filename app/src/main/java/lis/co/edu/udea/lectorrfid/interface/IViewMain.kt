package lis.co.edu.udea.lectorrfid.`interface`

import android.net.Uri

interface IViewMain {

    fun takePicture()

    fun showPreview(photo: Uri)

    fun showWaitPicture()

    fun hidePreview()

    fun dismissProgressDialog()

    fun keepScreenOn(isNeeded:Boolean = true):Unit
}