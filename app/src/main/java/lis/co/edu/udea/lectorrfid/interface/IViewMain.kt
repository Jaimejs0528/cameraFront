package lis.co.edu.udea.lectorrfid.`interface`

import android.graphics.Bitmap

interface IViewMain {

    fun takePicture()

    fun showPreview(photo:Bitmap)

    fun showPictureError()
}