package com.wolfsea.kotlinmvvm.adapter
import android.content.Context
import com.squareup.picasso.Picasso
import com.wolfsea.kotlinmvvm.R
import com.wolfsea.kotlinmvvm.bean.CardBean
import com.wolfsea.kotlinmvvm.databinding.ItemCardBinding

/**
 *@desc  卡片实体适配器
 *@author liuliheng
 *@time 2021/9/13  0:19
 **/
class CardABindingAdapter(context: Context) :
    BaseBindingAdapter<CardBean, ItemCardBinding>(context) {

    private val baseContext = context

    override fun onBindItem(binding: ItemCardBinding?, item: CardBean?, position: Int) {

        binding?.apply {
            textItemTitle.text = item?.name
            Picasso.with(baseContext).load(item?.resourceId!!).into(imageItemFlag)
        }
    }

    override fun getLayoutResId(viewType: Int): Int = R.layout.item_card
}