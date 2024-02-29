package com.example.schmecken.di

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.DigIngr
import com.example.domain.RecycIngr
import com.example.schmecken.R
import com.squareup.picasso.Picasso


class FirstViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val image: ImageView = view.findViewById(R.id.pictureOfIngr)
    private val label: TextView = view.findViewById(R.id.labelOfIngr)
    private val weight: TextView = view.findViewById(R.id.weightIngr)

    fun bind(item: RecycIngr) {
        label.text = item.label
        if (item.weight.length >= 4) {
            weight.text = item.weight.substring(0, 4)
        } else {
            weight.text = item.weight
        }
        if (!item.imageUrl.isNullOrEmpty()) {
        Picasso.get().load(item.imageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(image)
        } else {image.setImageResource(R.drawable.placeholder)}
    }
}

class FirstAdapter(private val items: List<RecycIngr>) : RecyclerView.Adapter<FirstViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inggredientitem, parent, false)
        return FirstViewHolder(view)
    }

    override fun onBindViewHolder(holder: FirstViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}


class SecondViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val digestView: TextView = view.findViewById(R.id.textView)
    private val valueView: TextView = view.findViewById(R.id.textwight)

    fun bind(item: DigIngr) {
        digestView.text = item.digest
        if (item.value.toString().length >= 4) {
            valueView.text = item.value.toString().substring(0, 4)
        } else {
            valueView.text = item.value.toString()
        }
    }
}

class SecondAdapter(private val items: List<DigIngr>) : RecyclerView.Adapter<SecondViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.iteminrecycler, parent, false)
        return SecondViewHolder(view)
    }

    override fun onBindViewHolder(holder: SecondViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
