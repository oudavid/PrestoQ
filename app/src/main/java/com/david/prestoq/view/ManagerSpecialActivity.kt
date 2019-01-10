package com.david.prestoq.view

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.david.prestoq.view.adapter.ManagerSpecialAdapter
import com.david.prestoq.R
import com.david.prestoq.viewmodel.ManagerSpecialsViewModel
import com.david.prestoq.viewmodel.NetworkException
import com.google.android.flexbox.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class ManagerSpecialActivity : AppCompatActivity() {
    private val tag: String = ManagerSpecialActivity::class.java.simpleName

    private lateinit var viewModel: ManagerSpecialsViewModel
    private lateinit var adapter: ManagerSpecialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide the action bar
        supportActionBar?.hide()

        // Get the device display metrics
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        Log.d(tag, "height: ${metrics.heightPixels} width: ${metrics.widthPixels}")

        // Instantiate the adapter with the display dimensions
        adapter = ManagerSpecialAdapter(displayWidth = metrics.widthPixels)
        manager_specials_rv.let {
            it.adapter = adapter

            // Use the flexbox layout manager to support list item wrapping and alignment requirements
            val layoutManager = FlexboxLayoutManager(this@ManagerSpecialActivity)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.flexWrap = FlexWrap.WRAP

            // Align items from their start positions
            layoutManager.alignItems = AlignItems.FLEX_START

            // Justify items so that they are centered if their leftover space in the row.
            layoutManager.justifyContent = JustifyContent.CENTER
            it.layoutManager = layoutManager

            // RecyclerView optimization
            it.setHasFixedSize(true)
        }

        // Bind this activity's lifecycle to the view model's lifecycle (to prevent memory leaks)
        viewModel = ViewModelProviders.of(this).get(ManagerSpecialsViewModel::class.java)
    }

    // Fetch and update manager specials every time the activity is active
    override fun onStart() {
        super.onStart()

        // Observe the manager specials live data and update data
        viewModel.getManagerSpecials().observe(this, Observer {
            if (it.managerSpecials.isEmpty()) { Snackbar.make(manager_specials_rv, getString(R.string.no_manager_specials), Snackbar.LENGTH_SHORT).show() }
            else {
                adapter.updateSpecials(it.managerSpecials)
                adapter.canvasUnits = it.canvasUnit
            }
        })

        // Observe any network (4xx HTTP errors) or server/connectivity issues (5xx)
        viewModel.listenForErrors().observe(this, Observer {
            when (it) {
                is NetworkException -> Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, R.string.no_network_connectivity_error_msg, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
