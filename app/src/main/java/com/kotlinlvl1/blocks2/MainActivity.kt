package com.kotlinlvl1.blocks2

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlinlvl1.blocks2.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class MainActivity : AppCompatActivity() {

//    private lateinit var recyclerView: RecyclerView
//    private lateinit var button: Button
    private lateinit var adapter: MainAdapter
    private lateinit var binding: ActivityMainBinding
    private val retrofitController by lazy {
        RetrofitController(API_URL)
    }
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        MainScope().launch {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)
        adapter = MainAdapter()
//        recyclerView = findViewById(R.id.recycleView)
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = GridLayoutManager(
            this,
            if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                PORTRAIT_SPAN
            } else {
                LANDSCAPE_SPAN
            }
        )
        savedInstanceState?.let {
            for (i in 0 until it.getInt(COUNT)) {
                adapter.items.add(Img())
            }
        }
        binding.but.setOnClickListener {
//            adapter.notifyItemInserted(adapter.items.size - 1)
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val imgResult = retrofitController.getImg()
                println("hello results")
                withContext(Dispatchers.Main){
                    when (imgResult){
                        is Result.OK -> {
                            adapter.items.add(imgResult.img)
                        }
                        is Result.Error -> {
                            setError(imgResult.error)
                        }
                    }
                }
//                adapter.items.add(Img())
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        outState.putInt(COUNT, adapter.itemCount)
    }

    private fun setError(error: String){
        println(error)
    }
    companion object{
        private const val COUNT = "count"
        private const val PORTRAIT_SPAN = 3
        private const val LANDSCAPE_SPAN = 4
        private const val API_URL = "https://api.punkapi.com/v2/"
    }

}