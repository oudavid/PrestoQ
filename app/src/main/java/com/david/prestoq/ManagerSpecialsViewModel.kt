package com.david.prestoq

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ManagerSpecialsViewModel: ViewModel() {
    private val TAG = ManagerSpecialsViewModel::class.java.simpleName
    private val managerSpecials = MutableLiveData<ManagerSpecialsResponse>()
    private val service: ManagerSpecialsService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://prestoq.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(ManagerSpecialsService::class.java)
    }

    fun getManagerSpecials(): LiveData<ManagerSpecialsResponse> {
        service.getManagerSpecials().enqueue(object: Callback<ManagerSpecialsResponse> {
            override fun onFailure(call: Call<ManagerSpecialsResponse>, t: Throwable) {
                Log.e(TAG, t.toString())
            }

            override fun onResponse(call: Call<ManagerSpecialsResponse>, response: Response<ManagerSpecialsResponse>) {
                if (response.isSuccessful) managerSpecials.value = response.body() else Log.e(TAG, response.toString())
            }
        })
        return managerSpecials
    }
}