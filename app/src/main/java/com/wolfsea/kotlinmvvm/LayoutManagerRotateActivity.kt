package com.wolfsea.kotlinmvvm
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wolfsea.kotlinmvvm.adapter.LayoutManagerRotateAdapter
import com.wolfsea.kotlinmvvm.databinding.ActivityLayoutManagerRotateBinding
import com.wolfsea.kotlinmvvm.layoutmanager.TurntableLayoutManager

class LayoutManagerRotateActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityLayoutManagerRotateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = ActivityLayoutManagerRotateBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        val images = mutableListOf(
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7,
            R.drawable.pic8,
            R.drawable.pic9,
            R.drawable.pic10,
            R.drawable.pic11,
        )

        dataBinding.rotateRv.apply {
            layoutManager = TurntableLayoutManager(2000, 12)
            itemAnimator = null
            adapter = LayoutManagerRotateAdapter(this@LayoutManagerRotateActivity).also {
                it.items.addAll(images)
            }
        }
    }
}