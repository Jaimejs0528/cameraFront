package lis.co.edu.udea.lectorrfid.util

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import lis.co.edu.udea.lectorrfid.view.activity.BaseActivity

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
    }

    object camera {
        @JvmStatic
        val CAMERA_PERMISSION = 1000
    }

}