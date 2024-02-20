package com.example.schmecken.di

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.schmecken.R
import com.squareup.picasso.Picasso
import com.example.domain.SimpleDish

class ItemAdapter(private val items: List<SimpleDish>, var context: Context) : RecyclerView.Adapter<ItemAdapter.myViewholder>(){
    class myViewholder(view: View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.label)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_template, parent,false)
        return myViewholder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: myViewholder, position: Int) {
        holder.title.text = items[position].name
        Picasso.get().load(items[position].image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.image)
    }
}