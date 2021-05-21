package com.example.infosapp_louaty.Interface

import com.example.infosapp_louaty.Model.Infos
import com.example.infosapp_louaty.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface InfosService{
    @get:GET("v2/sources?apiKey=b5754cedb46a44d8b608e71ce1412000")
    val sources: Call<WebSite>

    @GET
    fun getNewsFromSource(@Url url:String): Call<Infos>
}