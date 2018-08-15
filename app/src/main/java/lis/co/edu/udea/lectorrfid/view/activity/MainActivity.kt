package lis.co.edu.udea.lectorrfid.view.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import lis.co.edu.udea.lectorrfid.R
import lis.co.edu.udea.lectorrfid.`interface`.IViewMain
import lis.co.edu.udea.lectorrfid.presenter.MainPresenter

class MainActivity : BaseActivity(), IViewMain {
       var bSend: Button? = null
        lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createSnackBar(mainActivity_layout_container)
        initViews()
        initListeners()
    }

    private fun initViews() {
        mainPresenter = MainPresenter(this)
        mainPresenter.initCameraController()
        this.bSend = mainActivity_button_send

    }

    private fun initListeners() {

        bSend?.setOnClickListener {
            run {
                val toast = Toast.makeText(applicationContext, getString(R.string.mainActivity_string_messageEmptyPicture), Toast.LENGTH_SHORT)
                toast.show()
            }
        }
                ?: Toast.makeText(applicationContext, getString(R.string.system_string_message_errorToInflate), Toast.LENGTH_LONG).show()
    }

    override fun initCameraController() {
    }

    override fun showCamera() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
