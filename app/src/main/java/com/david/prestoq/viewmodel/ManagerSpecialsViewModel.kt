package com.david.prestoq.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.david.prestoq.data.ManagerSpecialsResponse
import com.david.prestoq.service.ManagerSpecialsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ManagerSpecialsViewModel: ViewModel() {
    private val tag: String = ManagerSpecialsViewModel::class.java.simpleName
    private val managerSpecials = MutableLiveData<ManagerSpecialsResponse>()
    private val errors = MutableLiveData<Throwable>()
    private val service: ManagerSpecialsService =
        ManagerSpecialsService.create()

    fun getManagerSpecials(): LiveData<ManagerSpecialsResponse> {
        service.getManagerSpecials().enqueue(object: Callback<ManagerSpecialsResponse> {
            override fun onFailure(call: Call<ManagerSpecialsResponse>, t: Throwable) {
                Log.e(tag, t.toString())
                errors.value = t
            }

            override fun onResponse(call: Call<ManagerSpecialsResponse>, response: Response<ManagerSpecialsResponse>) {
                if (response.isSuccessful) managerSpecials.value = response.body()
                else errors.value = NetworkException(
                    errorCode = response.code(),
                    text = response.message()
                )
            }
        })
        return managerSpecials
    }

    fun listenForErrors(): MutableLiveData<Throwable> = errors
}

class NetworkException(errorCode: Int, text: String): Exception("errorCode: $errorCode, message: $text")