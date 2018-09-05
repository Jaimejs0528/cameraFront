package lis.co.edu.udea.lectorrfid.`interface`

import lis.co.edu.udea.lectorrfid.DTO.responseDTO
import lis.co.edu.udea.lectorrfid.util.Tool.Service.SENDPHOTO
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface IService {

    @Multipart
    @POST(SENDPHOTO)
    fun sendImage(@Part image: MultipartBody.Part): Call<responseDTO>

}