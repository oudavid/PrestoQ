package com.david.prestoq

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

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
    val displayName: TextView = view.findViewById(R.id.display_name)!!

}