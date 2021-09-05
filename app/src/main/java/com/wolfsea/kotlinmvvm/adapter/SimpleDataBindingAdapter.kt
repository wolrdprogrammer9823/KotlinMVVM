package com.wolfsea.kotlinmvvm.adapter
import android.content.Context
import com.wolfsea.kotlinmvvm.R
import com.wolfsea.kotlinmvvm.bean.User
import com.wolfsea.kotlinmvvm.databinding.AdapterDataBindingBinding
import com.wolfsea.kotlinmvvm.extendmethod.toast

/**
 *@desc  简单BindingAdapter
 *@author liuliheng
 *@time 2021/9/4  16:48
 **/
class SimpleDataBindingAdapter(context: Context) :
    BaseBindingAdapter<User, AdapterDataBindingBinding>(context) {

    var btnClick: ((position: Int) -> Unit)? = null

    override fun getLayoutResId(viewType: Int): Int = R.layout.adapter_data_binding

    override fun onBindItem(binding: AdapterDataBindingBinding?, item: User?, position: Int) {
        item?.let {
            binding?.model = it
        }

        //点击事件处理
        binding?.rootCl?.setOnClickListener {
            //mContext.toast("${item?.name}")
            onViewClick?.invoke(position)
        }

        binding?.btnItem?.setOnClickListener {
            btnClick?.invoke(position)
        }
    }
}