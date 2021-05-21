package com.example.infosapp_louaty.Common


import com.example.infosapp_louaty.Interface.InfosService
import com.example.infosapp_louaty.Retrofit.RetrofitClient


object Common {
    val BASE_URL = "https://newsapi.org"
    val API_KEY  = "b5754cedb46a44d8b608e71ce1412000"

    val infosService: InfosService
        get() = RetrofitClient.getClient(BASE_URL).create(InfosService::class.java)


    fun getNewsAPI(source: String):String{
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
        return apiUrl
    }

}