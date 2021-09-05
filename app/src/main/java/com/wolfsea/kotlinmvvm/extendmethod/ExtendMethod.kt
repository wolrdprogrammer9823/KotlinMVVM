package com.wolfsea.kotlinmvvm.extendmethod
import android.content.Context
import android.widget.Toast

/**
 *@desc  扩展方式
 *@author liuliheng
 *@time 2021/9/4  22:29
 **/

fun Context?.toast(message:CharSequence,duration:Int = Toast.LENGTH_SHORT) = this?.let {
    Toast.makeText(this, message, duration).show()
}