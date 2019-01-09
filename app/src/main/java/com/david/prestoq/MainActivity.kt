package com.david.prestoq

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private lateinit var model: ManagerSpecialsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        model = ViewModelProviders.of(this).get(ManagerSpecialsViewModel::class.java)
        model.getManagerSpecials().observe(this, Observer {
            Log.d(TAG, it.toString())
        })
    }
}
