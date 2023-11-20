package com.kotlinlvl1.blocks2

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var button: Button
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MainAdapter()
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(
            this,
            if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                PORTRAIT_SPAN
            } else {
                LANDSCAPE_SPAN
            }
        )
        savedInstanceState?.let {
            for (i in 0 until it.getInt(COUNT)) {
                adapter.items.add(i)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count", adapter.itemCount)
    }

    companion object{
        private const val COUNT = "count"
        private const val PORTRAIT_SPAN = 3
        private const val LANDSCAPE_SPAN = 4
    }

}