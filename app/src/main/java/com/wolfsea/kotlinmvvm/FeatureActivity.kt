package com.wolfsea.kotlinmvvm
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wolfsea.kotlinmvvm.databinding.ActivityFeatureBinding

class FeatureActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFeatureBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFeatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}