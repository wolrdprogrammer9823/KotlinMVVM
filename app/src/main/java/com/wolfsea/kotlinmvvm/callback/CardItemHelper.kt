package com.wolfsea.kotlinmvvm.callback
import androidx.recyclerview.widget.RecyclerView

/**
 *@desc  CardItem帮助类
 *@author liuliheng
 *@time 2021/9/13  0:26
 **/
interface CardItemHelper {

    fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int)
}