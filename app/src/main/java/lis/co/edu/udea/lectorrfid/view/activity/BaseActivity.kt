package lis.co.edu.udea.lectorrfid.view.activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import lis.co.edu.udea.lectorrfid.R
import lis.co.edu.udea.lectorrfid.`interface`.IBase
import lis.co.edu.udea.lectorrfid.util.Tool

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), IBase {
    var mSnackBar: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    override fun createSnackBar(view: View) {
        mSnackBar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
    }

    override fun showSnackBar(message: Int, duration: Int, action: Boolean) {
        mSnackBar?.setText(message)
                ?: throw KotlinNullPointerException("No se ha creado el snackBar")
        mSnackBar?.duration = duration
        if (action) {
            mSnackBar?.setAction(R.string.mainActivity_string_actionSnackBarCamera
            ) {
                this.mSnackBar?.dismiss()
            }
        }
        mSnackBar?.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Tool.camera.CAMERA_PERMISSION) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showSnackBar(R.string.mainActivity_string_messageCameraPermission, Snackbar.LENGTH_INDEFINITE, true)
            }
        }
    }

}