package com.david.prestoq

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.activity_main.*


class ManagerSpecialActivity : AppCompatActivity() {
    private val tag: String = ManagerSpecialActivity::class.java.simpleName

    private lateinit var viewModel: ManagerSpecialsViewModel
    private lateinit var adapter: ManagerSpecialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        Log.d(tag, "height: ${metrics.heightPixels} width: ${metrics.widthPixels}")

        adapter = ManagerSpecialAdapter(displayWidth = metrics.widthPixels)
        manager_specials_rv.let {
            it.adapter = adapter
            val layoutManager = FlexboxLayoutManager(this@ManagerSpecialActivity)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.flexWrap = FlexWrap.WRAP
            layoutManager.alignItems = AlignItems.FLEX_START
            layoutManager.justifyContent = JustifyContent.CENTER
            it.layoutManager = layoutManager
            it.setHasFixedSize(true)
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel = ViewModelProviders.of(this).get(ManagerSpecialsViewModel::class.java)
        viewModel.getManagerSpecials().observe(this, Observer {
            Log.d(tag, it.toString())
            adapter.updateSpecials(it.managerSpecials)
            adapter.canvasUnits = it.canvasUnit
        })
        viewModel.listenForErrors().observe(this, Observer {
            when (it) {
                is NetworkException -> Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, R.string.no_network_connectivity_error_msg, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
