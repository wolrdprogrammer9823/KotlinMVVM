package com.wolfsea.kotlinmvvm
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfsea.kotlinmvvm.adapter.SimpleDataBindingAdapter
import com.wolfsea.kotlinmvvm.bean.ColumnData
import com.wolfsea.kotlinmvvm.bean.User
import com.wolfsea.kotlinmvvm.extendmethod.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {

        val mAdapter = SimpleDataBindingAdapter(this)
        mAdapter.items.apply {
            add(User("小红", 25, mutableListOf(ColumnData("12.5",11.0F,12.0F)),"item1"))
            add(User("小明", 35, mutableListOf(ColumnData("2.5", 1.0F, 2.0F)),"item2"))
            add(User("小假", 15, mutableListOf(ColumnData("102.5", 111.0F, 120.0F)),"item3"))
            add(User("小同", 28, mutableListOf(ColumnData("82.5", 81.0F, 82.0F)),"item4"))
            add(User("小国", 20, mutableListOf(ColumnData("1.5", 17.0F, 18.0F)),"item5"))
        }

        mAdapter.onViewClick = {
            toast(mAdapter.items[it].name)
        }

        mAdapter.btnClick = {
            toast(mAdapter.items[it].value)
        }

        mvvm_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }
    }
}