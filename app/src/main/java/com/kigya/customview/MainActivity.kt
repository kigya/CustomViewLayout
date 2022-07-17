package com.kigya.customview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kigya.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomButtons.setListener {
            if (it == BottomButtonAction.POSITIVE) {
                binding.bottomButtons.setPositiveButtonText("Updated OK")
                Toast.makeText(this, "Positive Button Pressed", Toast.LENGTH_SHORT).show()

            } else if (it == BottomButtonAction.NEGATIVE) {
                binding.bottomButtons.setNegativeButtonText("Updated Cancel")
                Toast.makeText(this, "Negative Button Pressed", Toast.LENGTH_SHORT).show()

            }
        }
    }
}