package com.example.myretrofit.API

import com.example.myretrofit.Models.MyRespond
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface MyRestAPI {

    companion object{
        val BASE_URL :String = "http://test.onmyfinger.com/"
    }


    @GET ("images/{id}")
    fun getImage(@Path("id") id:String): Call<MyRespond>

    @POST ("home/UploadImage")
    @FormUrlEncoded
    fun UploadImage(@Field("img") img: String, @Field("name") name:String): Call<MyRespond>

}