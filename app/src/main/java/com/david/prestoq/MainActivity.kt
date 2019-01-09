package com.david.prestoq

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val tag: String = MainActivity::class.java.simpleName

    private lateinit var viewModel: ManagerSpecialsViewModel

    private lateinit var adapter: ManagerSpecialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
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
                else -> Toast.makeText(this, "Server cannot be reached. Please check your internet connection.", Toast.LENGTH_SHORT).show()
            }
        })

        adapter = ManagerSpecialAdapter()
        manager_specials_rv.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            it.setHasFixedSize(true)
        }
    }
}

class ManagerSpecialAdapter(private var specials: List<ManagerSpecial> = emptyList()): RecyclerView.Adapter<ManagerSpecialViewHolder>() {

    override fun getItemCount(): Int {
        return specials.size
    }

    override fun onBindViewHolder(holder: ManagerSpecialViewHolder, position: Int) {

        val special = specials[position]

        holder.originalPrice.text = String.format("$%s", special.original_price)
        holder.originalPrice.paintFlags = holder.originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        holder.price.text = String.format("$%s", special.price)
        holder.displayName.text = special.display_name

        Glide
            .with(holder.itemView.context)
            .load(special.imageUrl)
            .into(holder.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerSpecialViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_special, parent, false)
        return ManagerSpecialViewHolder(view)
    }

    fun updateSpecials(list: List<ManagerSpecial>) {
        specials = list
        notifyDataSetChanged()
    }
}

class ManagerSpecialViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.image)!!
    val originalPrice: TextView = view.findViewById(R.id.original_price)!!
    val price: TextView = view.findViewById(R.id.price)!!
    val displayName:TextView = view.findViewById(R.id.display_name)!!

}
