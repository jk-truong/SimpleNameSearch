package com.example.simplenamesearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

private const val AGE_ENDPOINT = "https://api.agify.io/?name="
private const val GENDER_ENDPOINT = "https://api.genderize.io/?name="
private const val NATIONALITY_ENDPOINT = "https://api.nationalize.io/?name="

class NameSearchViewModel : ViewModel() {

    private val responseResultLiveData = MutableLiveData<ResponseResult>()

    private fun setResponseResult(responseResult: ResponseResult) {
        responseResultLiveData.postValue(responseResult)
    }

    fun getResponseResult(): MutableLiveData<ResponseResult> {
        return responseResultLiveData
    }

    suspend fun retrieveNameInfo(name: String) = withContext(Dispatchers.Default) {
        val client = HttpClient(CIO) {
            install(JsonFeature)
        }
        val ageEndpoint = "$AGE_ENDPOINT$name"
        val genderEndpoint = "$GENDER_ENDPOINT$name"
        val nationalityEndpoint = "$NATIONALITY_ENDPOINT$name"

        val ageResponse = async {
            client.get<Age>(ageEndpoint)
        }
        val genderResponse = async {
            client.get<Gender>(genderEndpoint)
        }
        val nationalityResponse = async {
            client.get<Nationality>(nationalityEndpoint)
        }

        setResponseResult(
            ResponseResult.parse(
                ageResponse.await(),
                genderResponse.await(),
                nationalityResponse.await()
            )
        )
    }
}