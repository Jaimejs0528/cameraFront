package lis.co.edu.udea.lectorrfid.view.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import lis.co.edu.udea.lectorrfid.R
import lis.co.edu.udea.lectorrfid.`interface`.IBase
import lis.co.edu.udea.lectorrfid.util.Tool

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), IBase {
    var mSnackBar: Snackbar? = null
    var mProgressDialog: ProgressDialog? = null

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
                val settings = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uriApp = Uri.fromParts(Tool.PACKAGE, this.packageName, null)
                settings.data = uriApp
                startActivity(settings)
                this.mSnackBar?.dismiss()
            }
        }
        mSnackBar?.show()
    }

    override fun createProgressDialog() {
        mProgressDialog = ProgressDialog(this)
    }

    override fun showProgressDialog(message: Int, typeProgress: Int, isInfinite: Boolean) {
        mProgressDialog?.setMessage(getString(message))
        if (isInfinite) {
            mProgressDialog?.setCancelable(false)
            mProgressDialog?.isIndeterminate = true
        }
        mProgressDialog?.show()
    }

    override fun hideProgressDialog() {
        if (mProgressDialog?.isShowing == true) {
            mProgressDialog?.dismiss()
            mProgressDialog = null
        }
    }


    override fun showToast(message: Int, duration: Int) {
        Toast.makeText(this, getText(message), duration).show()
    }

}