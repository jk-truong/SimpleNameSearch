package com.example.simplenamesearch.api

import com.example.simplenamesearch.Age
import com.example.simplenamesearch.Gender
import com.example.simplenamesearch.Nationality
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NameSearchApi {

    @GET("https://api.agify.io")
    fun fetchAge(@Query("name") name: String): Call<Age>

    @GET("https://api.genderize.io")
    fun fetchGender(@Query("name") name: String): Call<Gender>

    @GET("https://api.nationalize.io")
    fun fetchNationality(@Query("name") name: String): Call<Nationality>
}