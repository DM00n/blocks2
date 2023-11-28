package com.kotlinlvl1.blocks2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

    val items: ArrayList<Img> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, null)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

}

class MainViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val img: ImageView = itemView.findViewById(R.id.img)

    fun bind(item: Img) {
        CoroutineScope(Dispatchers.IO).launch {
            img.load(item.url)
        }


        val req = ImageRequest.Builder(img.context)
//        img.load(item.url) {
//            listener(
//                onError = { _, _ ->
//                    img.isClickable
//                    img.setImageResource(R.drawable.ic_launcher_foreground)
//                }
//            )
//        }
    }
}