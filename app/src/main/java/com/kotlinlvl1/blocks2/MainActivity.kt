package com.kotlinlvl1.blocks2

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kotlinlvl1.blocks2.databinding.ActivityMainBinding
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter
    private lateinit var binding: ActivityMainBinding
    private val retrofitController by lazy {
        RetrofitController(API_URL)
    }
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        MainScope().launch {
            val snackbar =
                Snackbar.make(binding.linLay, "Ошибка", Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = MainAdapter()
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = GridLayoutManager(
            this,
            if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                PORTRAIT_SPAN
            } else {
                LANDSCAPE_SPAN
            }
        )
//        savedInstanceState?.let {
//            for (i in 0 until it.getInt(COUNT)) {
//                adapter.items.add(Img())
//            }
//        }
        binding.but.setOnClickListener {
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val imgResult = retrofitController.getImg()
                withContext(Dispatchers.Main){
                    when (imgResult){
                        is Result.OK -> {
                            adapter.items.add(imgResult.img)
                            adapter.notifyItemInserted(adapter.itemCount-1)
                        }
                        is Result.Error -> {
//                            setError(imgResult.error)
                        }
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNT, adapter.itemCount)
    }

//    private fun setError(error: String){
//        println(error)
//    }
    companion object{
        private const val COUNT = "count"
        private const val PORTRAIT_SPAN = 2
        private const val LANDSCAPE_SPAN = 3
        private const val API_URL = "https://api.punkapi.com/v2/"
    }

}