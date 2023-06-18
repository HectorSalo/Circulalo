package com.skysam.hchirinos.circulalo.ui.post

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skysam.hchirinos.circulalo.R
import com.skysam.hchirinos.circulalo.dataClass.Category

/**
 * Created by Hector Chirinos on 08/05/2023.
 */

class CategoryAdapter(private val categories: MutableList<Category>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
 lateinit var context: Context

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
  val view = LayoutInflater.from(parent.context)
   .inflate(R.layout.list_view_categories, parent, false)
  context = parent.context
  return ViewHolder(view)
 }

 override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
  val item = categories[position]
  holder.category.text = item.name
  holder.checkbox.isChecked = item.selected
 }

 override fun getItemCount(): Int = categories.size

 inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val checkbox: CheckBox = view.findViewById(R.id.checkBox)
  val category: TextView = view.findViewById(R.id.tv_category)
 }
}