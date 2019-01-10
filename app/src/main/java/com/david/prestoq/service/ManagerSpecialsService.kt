package com.david.prestoq.service

import com.david.prestoq.data.ManagerSpecialsResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ManagerSpecialsService {

    @GET("android-coding-challenge")
    fun getManagerSpecials(): Call<ManagerSpecialsResponse>

    companion object {
        fun create(): ManagerSpecialsService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://prestoq.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ManagerSpecialsService::class.java)
        }
    }
}