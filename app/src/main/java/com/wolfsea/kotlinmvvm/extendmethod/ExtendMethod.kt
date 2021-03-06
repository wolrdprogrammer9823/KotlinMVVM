package com.wolfsea.kotlinmvvm.extendmethod
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 *@desc  扩展方式
 *@author liuliheng
 *@time 2021/9/4  22:29
 **/

fun Context?.toast(message:CharSequence,duration:Int = Toast.LENGTH_SHORT) = this?.let {
    Toast.makeText(this, message, duration).show()
}

inline fun <reified T : Activity> Context?.startActivity() {
    val intent = Intent(this, T::class.java)
    this?.startActivity(intent)
}