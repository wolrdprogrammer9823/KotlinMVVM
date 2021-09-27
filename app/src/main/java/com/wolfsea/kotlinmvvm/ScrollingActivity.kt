package com.wolfsea.kotlinmvvm

import android.os.Bundle
import com.wolfsea.kotlinmvvm.extendmethod.startActivity
import androidx.appcompat.app.AppCompatActivity
import com.wolfsea.kotlinmvvm.databinding.ActivityScrollingBinding

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardBtn.setOnClickListener {

            startActivity<FeatureActivity>()
        }

        binding.rotateBtn.setOnClickListener {
            startActivity<LayoutManagerRotateActivity>()
        }

        binding.defineBtn.setOnClickListener {
            startActivity<MainActivity>()
        }
    }
}