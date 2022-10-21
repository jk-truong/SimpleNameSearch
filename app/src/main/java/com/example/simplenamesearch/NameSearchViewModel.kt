package com.example.simplenamesearch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplenamesearch.api.NameSearchApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NameSearchViewModel : ViewModel() {

    private val nameSearchApi: NameSearchApi
    private val ageLiveData = MutableLiveData<Age>()
    private val genderLiveData = MutableLiveData<Gender>()
    private val nationalityLiveData = MutableLiveData<Nationality>()

    fun setAge(age: Age) {
        ageLiveData.value = age
    }

    fun getAge(): MutableLiveData<Age> {
        return ageLiveData
    }

    fun setGender(gender: Gender) {
        genderLiveData.value = gender
    }

    fun getGender(): MutableLiveData<Gender> {
        return genderLiveData
    }

    fun setNationality(nationality: Nationality) {
        nationalityLiveData.value = nationality
    }

    fun getNationality(): MutableLiveData<Nationality> {
        return nationalityLiveData
    }

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://storage.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        nameSearchApi = retrofit.create(NameSearchApi::class.java)
    }

    suspend fun retrieveNameInfo(name: String) = withContext(Dispatchers.IO) {
        async { fetchAgeInfo(name) }
        async { fetchGenderInfo(name) }
        async { fetchNationalityInfo(name) }
    }.await()

    private fun fetchAgeInfo(name: String) {
        val responseLiveData: MutableLiveData<Age> = MutableLiveData()
        val ageRequest: Call<Age> = nameSearchApi.fetchAge(name)

        ageRequest.enqueue(object : Callback<Age> {
            override fun onResponse(call: Call<Age>, response: Response<Age>) {
                val body = Gson().toJson(response.body())
                Log.d(javaClass.simpleName, "Response received $body")

                val ageResponse = Gson().fromJson(body, Age::class.java)
                responseLiveData.value = ageResponse
                setAge(ageResponse)
                Log.d(javaClass.simpleName, "Age livedata ${getAge().value}")
            }

            override fun onFailure(call: Call<Age>, t: Throwable) {
                Log.e(javaClass.simpleName, "Failed to fetch age. ${t.cause}")
            }
        })
    }

    private fun fetchGenderInfo(name: String) {
        val responseLiveData: MutableLiveData<Gender> = MutableLiveData()
        val ageRequest: Call<Gender> = nameSearchApi.fetchGender(name)

        ageRequest.enqueue(object : Callback<Gender> {
            override fun onResponse(call: Call<Gender>, response: Response<Gender>) {
                val body = Gson().toJson(response.body())
                Log.d(javaClass.simpleName, "Response received $body")

                val ageResponse = Gson().fromJson(body, Gender::class.java)
                responseLiveData.value = ageResponse
                setGender(ageResponse)
                Log.d(javaClass.simpleName, "Age livedata ${getGender().value}")
            }

            override fun onFailure(call: Call<Gender>, t: Throwable) {
                Log.e(javaClass.simpleName, "Failed to fetch gender. ${t.cause}")
            }
        })
    }

    private fun fetchNationalityInfo(name: String) {
        val responseLiveData: MutableLiveData<Nationality> = MutableLiveData()
        val ageRequest: Call<Nationality> = nameSearchApi.fetchNationality(name)

        ageRequest.enqueue(object : Callback<Nationality> {
            override fun onResponse(call: Call<Nationality>, response: Response<Nationality>) {
                val body = Gson().toJson(response.body())
                Log.d(javaClass.simpleName, "Response received $body")

                val ageResponse = Gson().fromJson(body, Nationality::class.java)
                responseLiveData.value = ageResponse
                setNationality(ageResponse)
                Log.d(javaClass.simpleName, "Age livedata ${getNationality().value}")
            }

            override fun onFailure(call: Call<Nationality>, t: Throwable) {
                Log.e(javaClass.simpleName, "Failed to fetch nationality. ${t.cause}")
            }
        })
    }

}