package com.ami.covidindonesiaupdate.network

import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("provinsi")
    fun getAllProvinsi() : retrofit2.Call<Response>
}
interface infoService{
    @GET
    fun getINfoService(@Url url:String?)
}
object RetrofitBuilder{
    private val okhttp = OkHttpClient().newBuilder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()


    val retrofit = Retrofit.Builder()
        .baseUrl("https://indonesia-covid-19.mathdro.id/api/provinsi")
        .client(okhttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}