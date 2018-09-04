package lis.co.edu.udea.lectorrfid.util

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.support.v4.app.ActivityCompat
import lis.co.edu.udea.lectorrfid.view.activity.BaseActivity
import java.io.File

class Tool {

    companion object {
        @JvmStatic
        fun hasPermission(code: String, context: Context) = ActivityCompat.checkSelfPermission(context,
                code) == PackageManager.PERMISSION_GRANTED

        @JvmStatic
        fun makeRequest(permissions: ArrayList<String>, requestCode: Int, baseActivity: BaseActivity) {
            for (permission in permissions) {
                ActivityCompat.requestPermissions(baseActivity,
                        arrayOf(permission),
                        requestCode)
            }
        }

        @JvmStatic
        fun rotateBitmap(source: Bitmap, degree: Float): Bitmap {
            val width = source.width
            val height = source.height
            val matrix = Matrix()
            matrix.setRotate(degree)
            return Bitmap.createBitmap(source, 0, 0, width, height, matrix, true)
        }

        @JvmStatic
        fun deletePhotoFile(photo: File) = photo.delete()

    }

    object camera {
        @JvmStatic
        val CAMERA_PERMISSION = 1000
        val JPG_IMAGE = ".jpg"
        val PNG_IMAGE = ".png"
        val URI_STATE: String = "URI_PHOTO"
    }

}