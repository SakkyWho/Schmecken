package com.example.schmecken.di

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.domain.DomainPresentationProvider
import com.example.domain.SimpleDish
import com.example.domain.bitmapdata
import com.example.schmecken.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.lang.Math.min

class ItemAdapter(
    internal var items: MutableList<SimpleDish>,
    var context: Context,
    private val viewModel: SharedViewModel
) : RecyclerView.Adapter<ItemAdapter.MyViewholder>() {
    private var onItemClickListener: ((SimpleDish) -> Unit)? = null

    fun addItems(newItems: List<SimpleDish>) {
        val startPosition = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startPosition, newItems.size)
    }

    class MyViewholder(view: View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.label)
        var starButton: ImageButton = view.findViewById<ImageButton>(R.id.likedButton)
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
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val item = items[position]
        holder.title.text = item.name

        val bitmapDataList = viewModel.getBitmapDataList().value ?: emptyList()

        val matchingBitmapData = bitmapDataList.find { it.id == item.id }
        if (matchingBitmapData != null) {
            if (matchingBitmapData.isLiked) {
                holder.starButton.setImageResource(R.drawable.bluemball)
                holder.starButton.tag = R.drawable.bluemball
            } else {
                holder.starButton.setImageResource(R.drawable.truemball)
                holder.starButton.tag = R.drawable.truemball
            }
        } else {
            holder.starButton.setImageResource(R.drawable.truemball)
            holder.starButton.tag = R.drawable.truemball
        }

        if (matchingBitmapData != null) {
            val bitmap = BitmapFactory.decodeByteArray(matchingBitmapData.imageBitmap, 0, matchingBitmapData.imageBitmap?.size ?: 0)
            holder.image.setImageBitmap(bitmap)
        } else {
            Glide.with(holder.itemView)
                .load(item.image)
                .placeholder(R.drawable.placeholder)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        holder.image.setImageDrawable(resource)

                        val bitmap = (resource as BitmapDrawable).bitmap

                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val byteArray = stream.toByteArray()

                        val newBitmapData = bitmapdata(id = item.id, imageBitmap = byteArray)
                        viewModel.addBitmapData(newBitmapData)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Обрабатываем очистку ресурса
                    }
                })
        }

        holder.starButton.setOnClickListener {
            val isLiked = holder.starButton.tag == R.drawable.truemball
            if (isLiked) {
                holder.starButton.setImageResource(R.drawable.bluemball)
                holder.starButton.tag = R.drawable.bluemball
            } else {
                holder.starButton.setImageResource(R.drawable.truemball)
                holder.starButton.tag = R.drawable.truemball
            }
            viewModel.updateIsLiked(item.id, !isLiked)
        }

    }
    fun updateItems(newItems: List<SimpleDish>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    fun setOnItemClickListener(listener: (SimpleDish) -> Unit) {
        onItemClickListener = listener
    }
}
fun convertToSimpleDishList(bitmapdataList: List<bitmapdata>): List<SimpleDish> {
    val simpleDishList = mutableListOf<SimpleDish>()

    for (bitmapdata in bitmapdataList) {
        // Здесь вы преобразуете каждый объект bitmapdata в объект SimpleDish
        // и добавляете его в список simpleDishList
    }

    return simpleDishList
}