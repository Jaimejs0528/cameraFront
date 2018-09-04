package lis.co.edu.udea.lectorrfid.model

import lis.co.edu.udea.lectorrfid.R
import lis.co.edu.udea.lectorrfid.`interface`.IService
import lis.co.edu.udea.lectorrfid.presenter.MainPresenter
import lis.co.edu.udea.lectorrfid.util.Tool
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class ServiceController(val mPresenter: MainPresenter) {

    private val client = OkHttpClient.Builder().build()
    private val service = Retrofit.Builder().baseUrl(Tool
            .Service.BASE).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(IService::class.java)

    fun sendImage(photoImage: File) {
        val reqFile = RequestBody.create(MediaType.parse("image/*"), photoImage)
        val body = MultipartBody.Part.createFormData("upload", photoImage.name, reqFile)

        val request = service.sendImage(body)
        request.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
                mPresenter.showResponse(R.string.mainActivity_string_messageNull)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
            }


        })
    }

}