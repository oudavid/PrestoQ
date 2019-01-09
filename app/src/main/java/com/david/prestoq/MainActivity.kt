package com.david.prestoq

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private lateinit var viewModel: ManagerSpecialsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        viewModel = ViewModelProviders.of(this).get(ManagerSpecialsViewModel::class.java)
        viewModel.getManagerSpecials().observe(this, Observer {
            Log.d(TAG, it.toString())
        })
        viewModel.listenForErrors().observe(this, Observer {
            when (it) {
                is NetworkException -> Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Server cannot be reached. Please check your internet connection.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
