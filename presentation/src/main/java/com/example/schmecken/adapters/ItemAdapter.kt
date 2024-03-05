package com.example.schmecken.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.domain.dataclasses.SimpleDish
import com.example.domain.dataclasses.domeindata
import com.example.schmecken.R
import com.example.schmecken.di.SharedViewModel
import java.io.ByteArrayOutputStream

class ItemAdapter(
    internal var items: MutableList<Any>,
    var context: Context,
    private val viewModel: SharedViewModel
) : RecyclerView.Adapter<ItemAdapter.MyViewholder>() {
    private var onItemClickListener: ((SimpleDish) -> Unit)? = null

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
                    onItemClickListener?.invoke(items[position] as SimpleDish)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val item = items[position]
        if (item is domeindata && viewModel.isSwitchChecked.value == true) {
            holder.title.text = item.label
            updateStar(holder.starButton, item.isLiked)
            val bitmap = BitmapFactory.decodeByteArray(item.imageBitmap, 0, item.imageBitmap?.size ?: 0)
            holder.image.setImageBitmap(bitmap)
            holder.itemView.setOnClickListener {
                viewModel.selectedItemId.value = item.id
            }
        } else if (item is SimpleDish) {
            holder.title.text = item.name
            updateStar(holder.starButton, item.isLiked)
            Glide.with(holder.itemView)
                .asBitmap()
                .load(item.image)
                .placeholder(R.drawable.placeholder)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        holder.image.setImageBitmap(resource)
                        val stream = ByteArrayOutputStream()
                        resource.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val byteArray = stream.toByteArray()
                        val matchingBitmapData = domeindata(id = item.id, imageBitmap = byteArray)
                        viewModel.addBitmapData(matchingBitmapData)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
            // По нажатию на предмет передать в selectedItemId Id
            holder.itemView.setOnClickListener {
                viewModel.selectedItemId.value = item.id
            }
        }

        holder.starButton.setOnClickListener {
            if (item is domeindata) {
                item.isLiked = !item.isLiked
                updateStar(holder.starButton, item.isLiked)
                viewModel.removeUnlikedItems()
                viewModel.loadtodb()
            } else if (item is SimpleDish) {
                item.isLiked = !item.isLiked
                updateStar(holder.starButton, item.isLiked)
                viewModel.removeUnlikedItems()
                viewModel.loadtodb()
            }
        }
    }

    fun updateStar(starButton: ImageButton, isLiked: Boolean?) {
        starButton.setImageResource(if (isLiked == true) R.drawable.bluemball else R.drawable.truemball)
        starButton.tag = if (isLiked == true) R.drawable.bluemball else R.drawable.truemball
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