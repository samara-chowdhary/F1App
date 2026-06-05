package com.example.f1app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitClient {

    private val mapper = ObjectMapper().apply {
        propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
    }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openf1.org/v1/")
        .addConverterFactory(JacksonConverterFactory.create(mapper))
        .build()
}
