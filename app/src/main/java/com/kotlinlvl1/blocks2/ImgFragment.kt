package com.kotlinlvl1.blocks2

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kotlinlvl1.blocks2.databinding.FragmentBinding
import kotlinx.coroutines.*

class ImgFragment : Fragment() {

    private lateinit var adapter: MainAdapter
    private lateinit var binding: FragmentBinding
    private val retrofitController by lazy {
        RetrofitController(API_URL)
    }
    private val cache = Cache

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        MainScope().launch {
            val snackbar =
                Snackbar.make(binding.linLay, R.string.errorCon, Snackbar.LENGTH_SHORT)
            snackbar.show()
            binding.pb.visibility = View.INVISIBLE
            binding.recycleView.alpha = 0.5F
            binding.recycleView.setOnTouchListener { _, _ ->  true}
            binding.buttons.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBinding.bind(view)
        adapter = MainAdapter()
        binding.recycleView.adapter = adapter

        CURRENT_SPAN =
            if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                PORTRAIT_SPAN
            } else {
                LANDSCAPE_SPAN
            }

        binding.recycleView.layoutManager = GridLayoutManager(
            requireContext(),
            CURRENT_SPAN
        )

        if (cache.getCache().isEmpty()) {
            binding.pb.visibility = View.VISIBLE
            getContent(ON_PAGE, CURRENT_PAGE)
        } else {
            for (img in cache.getCache()) {
                adapter.items.add(img)
            }
            adapter.notifyItemInserted(adapter.itemCount - 1)
        }


        binding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.pb.visibility = View.VISIBLE
                    getContent(ON_PAGE, CURRENT_PAGE)
                }
            }
        })

        binding.retryButton.setOnClickListener {
            binding.buttons.visibility = View.INVISIBLE
            binding.pb.visibility = View.VISIBLE
            binding.recycleView.setOnTouchListener { _, _ ->  false}
            binding.recycleView.alpha = 1F
            getContent(ON_PAGE, CURRENT_PAGE)
        }

        binding.cancelButton.setOnClickListener {
            binding.buttons.visibility = View.INVISIBLE
            binding.recycleView.setOnTouchListener { _, _ ->  false}
            binding.recycleView.alpha = 1F
        }
    }

    private fun getContent(pageSize: Int, page: Int) {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val imgResult = retrofitController.getImg(pageSize, page)
            withContext(Dispatchers.Main) {
                for (img in imgResult) {
                    when (img) {
                        is Result.OK -> {
                            adapter.items.add(img.img)
                            adapter.notifyItemInserted(adapter.itemCount - 1)
                            cache.fillCache(img.img)
                        }
                        is Result.Error -> {
//                       setError(imgResult.error)
                        }
                    }
                }
                binding.pb.visibility = View.INVISIBLE
            }
            CURRENT_PAGE++
        }
    }


    companion object {
        private const val PORTRAIT_SPAN = 2
        private const val LANDSCAPE_SPAN = 3
        private const val API_URL = "https://api.punkapi.com/v2/"
        private var CURRENT_SPAN = 0
        private var CURRENT_PAGE = 1
        private var ON_PAGE = 6
    }
}