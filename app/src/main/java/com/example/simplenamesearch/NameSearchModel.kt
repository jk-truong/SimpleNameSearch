package com.example.simplenamesearch

import com.google.gson.annotations.SerializedName

data class Age(
    @SerializedName("age") var age: String,
    @SerializedName("count") var count: String,
    @SerializedName("name") var name: String
)

data class Gender(
    @SerializedName("count") var count: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("name") var name: String,
    @SerializedName("probability") var probability: String
)

data class Nationality(
    @SerializedName("country") var country: List<Country>,
    @SerializedName("name") var name: String
)

data class Country(
    @SerializedName("country_id") var countryId: String,
    @SerializedName("probability") var probability: String
)

data class ResponseResult(
    val age: Age,
    val gender: Gender,
    val nationality: Nationality
) {
    companion object {
        fun parse(
            age: Age,
            gender: Gender,
            nationality: Nationality
        ): ResponseResult {
            return ResponseResult(
                age = age,
                gender = gender,
                nationality = nationality
            )
        }
    }

}