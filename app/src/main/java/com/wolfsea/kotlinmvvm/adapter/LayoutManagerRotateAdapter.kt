package com.wolfsea.kotlinmvvm.adapter
import android.content.Context
import com.wolfsea.kotlinmvvm.R
import com.wolfsea.kotlinmvvm.databinding.ItemTurntableBinding

/**
 *@desc  旋转的LayoutManager
 *@author liuliheng
 *@time 2021/9/28  0:08
 **/
class LayoutManagerRotateAdapter(context: Context) :
    BaseBindingAdapter<Int, ItemTurntableBinding>(context) {

    override fun getLayoutResId(viewType: Int): Int = R.layout.item_turntable

    override fun onBindItem(binding: ItemTurntableBinding?, item: Int?, position: Int) {
        binding?.itemTurntableImage?.setImageResource(item!!)
    }
}