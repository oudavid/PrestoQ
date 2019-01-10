package com.david.prestoq.view.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.david.prestoq.R
import com.david.prestoq.data.ManagerSpecial
import java.lang.IllegalArgumentException

class ManagerSpecialAdapter(
    private var specials: List<ManagerSpecial> = emptyList(),
    private var displayWidth: Int
): RecyclerView.Adapter<ManagerSpecialViewHolder>() {

    // Default max canvas units
    var canvasUnits: Int = 16

    // Util class to calculate adjusted item view widths and heights
    companion object LayoutParamsUtil {
        fun getHeight(displayWidth: Int, itemHeight: Int, canvasUnits: Int, layoutParams: ViewGroup.MarginLayoutParams): Int {
            return (displayWidth * itemHeight / canvasUnits) - layoutParams.topMargin - layoutParams.bottomMargin
        }

        fun getWidth(displayWidth: Int, itemWidth: Int, canvasUnits: Int, layoutParams: ViewGroup.MarginLayoutParams): Int {
            if (itemWidth > canvasUnits) throw IllegalArgumentException("Item width must be less than or equal to canvas units!")
            return (displayWidth * itemWidth / canvasUnits) - layoutParams.leftMargin - layoutParams.rightMargin
        }
    }

    override fun getItemCount(): Int {
        return specials.size
    }

    override fun onBindViewHolder(holder: ManagerSpecialViewHolder, position: Int) {
        val special = specials[position]
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height = getHeight(
            displayWidth,
            special.height,
            canvasUnits,
            layoutParams as ViewGroup.MarginLayoutParams
        )
        layoutParams.width = getWidth(
            displayWidth,
            special.width,
            canvasUnits,
            layoutParams
        )

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