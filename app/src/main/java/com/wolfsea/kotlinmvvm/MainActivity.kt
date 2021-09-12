package com.wolfsea.kotlinmvvm
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfsea.kotlinmvvm.adapter.SimpleDataBindingAdapter
import com.wolfsea.kotlinmvvm.bean.ColumnData
import com.wolfsea.kotlinmvvm.bean.User
import com.wolfsea.kotlinmvvm.extendmethod.toast
import com.wolfsea.kotlinmvvm.layoutmanager.CustomLayoutManager
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
            add(User("小国", 20, mutableListOf(ColumnData("1.5", 27.0F, 118.0F)),"item5"))
            add(User("小钱", 21, mutableListOf(ColumnData("2.5", 107.0F, 8.0F)),"item6"))
            add(User("小强", 22, mutableListOf(ColumnData("1.8", 7.0F, 28.0F)),"item7"))
            add(User("小和", 23, mutableListOf(ColumnData("4.0", 12.0F, 23.0F)),"item8"))
            add(User("小戴", 24, mutableListOf(ColumnData("11.5", 45.0F, 38.0F)),"item9"))
            add(User("小挺", 25, mutableListOf(ColumnData("10.1", 28.0F, 18.0F)),"item10"))
        }

        mAdapter.onViewClick = {
            toast(mAdapter.items[it].name)
        }

        mAdapter.btnClick = {
            toast(mAdapter.items[it].value)
        }

        mvvm_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            //layoutManager = CustomLayoutManager()
            adapter = mAdapter
        }
    }
}