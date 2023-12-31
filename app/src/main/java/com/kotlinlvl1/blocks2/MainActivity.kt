package com.kotlinlvl1.blocks2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotlinlvl1.blocks2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.fragment, ImgFragment()).commit()

    }
}