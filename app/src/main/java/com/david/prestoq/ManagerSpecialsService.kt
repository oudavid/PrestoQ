package com.david.prestoq

import retrofit2.Call
import retrofit2.http.GET

interface ManagerSpecialsService {

    @GET("android-coding-challenge")
    fun getManagerSpecials(): Call<ManagerSpecialsResponse>
}