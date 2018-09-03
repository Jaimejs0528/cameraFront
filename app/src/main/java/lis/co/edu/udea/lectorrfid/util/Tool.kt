package lis.co.edu.udea.lectorrfid.util

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.support.v4.app.ActivityCompat
import lis.co.edu.udea.lectorrfid.view.activity.BaseActivity
import java.io.File

class Tool {

    companion object {
//        @JvmStatic fun emptyorNull(String validat){
//        }

        @JvmStatic
        fun hasPermission(code: String, context: Context) = ActivityCompat.checkSelfPermission(context,
                code) == PackageManager.PERMISSION_GRANTED

        @JvmStatic
        fun makeRequest(permission: String, requestCode: Int, baseActivity: BaseActivity) {
            ActivityCompat.requestPermissions(baseActivity,
                    arrayOf(permission),
                    requestCode)
        }

        @JvmStatic
        fun rotateBitmap(source: Bitmap, degree: Float): Bitmap {
            val width = source.width
            val height = source.height
            val matrix: Matrix = Matrix()
            matrix.setRotate(degree)
            return Bitmap.createBitmap(source, 0, 0, width, height, matrix, true)
        }

        @JvmStatic
        fun deletePhotoFile(photo: Uri): Boolean {
            val file = File(photo.toString())
            return file.delete()
        }
    }

    object camera {
        @JvmStatic
        val CAMERA_PERMISSION = 1000
        val JPG_IMAGE = ".jpg"
        val PNG_IMAGE = ".png"
        val URI_STATE: String = "URI_PHOTO"
    }

}