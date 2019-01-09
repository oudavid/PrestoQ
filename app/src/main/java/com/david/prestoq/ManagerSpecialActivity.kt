package com.david.prestoq

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class ManagerSpecialActivity : AppCompatActivity() {
    private val tag: String = ManagerSpecialActivity::class.java.simpleName

    private lateinit var viewModel: ManagerSpecialsViewModel

    private lateinit var adapter: ManagerSpecialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        adapter = ManagerSpecialAdapter()
        manager_specials_rv.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this@ManagerSpecialActivity, RecyclerView.VERTICAL, false)
            it.setHasFixedSize(true)
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel = ViewModelProviders.of(this).get(ManagerSpecialsViewModel::class.java)
        viewModel.getManagerSpecials().observe(this, Observer {
            Log.d(tag, it.toString())
            adapter.updateSpecials(it.managerSpecials)
        })
        viewModel.listenForErrors().observe(this, Observer {
            when (it) {
                is NetworkException -> Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, R.string.no_network_connectivity_error_msg, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
