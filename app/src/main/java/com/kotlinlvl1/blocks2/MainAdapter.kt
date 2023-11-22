package com.kotlinlvl1.blocks2

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

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

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val img: ImageView = itemView.findViewById(R.id.img)

    fun bind(item: Img) {
        println(item)
        img.load(item.url){
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }
    }
}