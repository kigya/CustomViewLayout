package com.kigya.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kigya.customview.databinding.ActivityMainBinding
import com.kigya.customview.databinding.PartButtonsBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}