package com.example.schmecken.di

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.SimpleDish
import com.example.schmecken.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemAdapter(internal var items: List<SimpleDish>, var context: Context, private val viewModel: SharedViewModel) : RecyclerView.Adapter<ItemAdapter.MyViewholder>() {
    private var onItemClickListener: ((SimpleDish) -> Unit)? = null

    class MyViewholder(view: View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.label)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_template, parent,false)
        return MyViewholder(view).apply {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(items[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.title.text = items[position].name


        holder.image.setImageResource(R.drawable.placeholder)

        viewModel.viewModelScope.launch(Dispatchers.IO) {
            val bitmap = viewModel.loadBitmap(items[position].id)
            withContext(Dispatchers.Main) {
                holder.image.setImageBitmap(bitmap)
            }
        }
    }



    fun setOnItemClickListener(listener: (SimpleDish) -> Unit) {
        onItemClickListener = listener
    }
}
