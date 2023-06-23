package com.skysam.hchirinos.circulalo.ui.post

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skysam.hchirinos.circulalo.R

class CarouselAdapter(private val images: MutableList<Bitmap>): RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_image_carousel, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselAdapter.ViewHolder, position: Int) {
        val item = images[position]
        Glide.with(context)
            .load(item)
            .centerCrop()
            .placeholder(R.drawable.ic_add_image_88)
            .error(R.drawable.ic_add_image_88)
            .into(holder.image)
    }

    override fun getItemCount(): Int = images.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.carousel_image_view)
    }
}