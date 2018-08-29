package lis.co.edu.udea.lectorrfid.`interface`

import android.support.design.widget.Snackbar
import android.view.View

interface IBase {

    fun createSnackBar(view: View)

    fun showSnackBar(message: Int, duration: Int = Snackbar.LENGTH_SHORT, action: Boolean = false)

    fun createProgressDialog()

    fun showProgressDialog(message: Int,typeProgress:Int,isInfinite:Boolean=true)

    fun hideProgressDialog()
}