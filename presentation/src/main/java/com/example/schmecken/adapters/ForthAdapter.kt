package com.example.schmecken.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.dataclasses.FourthInfo
import com.example.schmecken.R
import com.example.schmecken.di.SharedViewModel
import com.squareup.picasso.Picasso

class forthAdapter(
    internal var items: List<FourthInfo>,
    private val context: Context,
    private val viewModel: SharedViewModel
) : RecyclerView.Adapter<forthAdapter.ViewHolder>(), AdapterView.OnItemSelectedListener{

    // Добавляем интерфейс для обработки нажатий
    interface OnItemClickListener {
        fun onItemClick(item: FourthInfo)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pictureOfDish: ImageView = view.findViewById(R.id.pictureOfdish)
        val labelOfFirst: TextView = view.findViewById(R.id.labelOffirst)
        val labelOfCalories: TextView = view.findViewById(R.id.labelOfcalories)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.twodishes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Set up ImageView and TextViews
        val item = items[position]
        Picasso.get()
            .load(item.imageUrl)
            .placeholder(R.drawable.placeholder)
            .into(holder.pictureOfDish)
        holder.labelOfFirst.text = item.label
        holder.labelOfCalories.text = item.calories.toString()

        // Set up click listener
        holder.itemView.setOnClickListener {
            listener?.onItemClick(item)
        }
    }

    override fun getItemCount() = items.size

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val item = parent.getItemAtPosition(position)
        // Handle Spinner item selection
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Handle when nothing is selected in Spinner
    }
}
