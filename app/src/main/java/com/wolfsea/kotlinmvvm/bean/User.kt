package com.wolfsea.kotlinmvvm.bean
import androidx.databinding.BindingAdapter
import com.wolfsea.kotlinmvvm.defineview.TableListInfoView

/**
 *@desc  实体类
 *@author liuliheng
 *@time 2021/9/4  16:45
 **/
data class User(
    val name: String,
    val age: Int,
    val columnDataSet: MutableList<ColumnData>?,
    var value: String
) {

    companion object {

        @BindingAdapter("bind:listDataSet")
        @JvmStatic
        fun setTabListData(
            tableListInfoView: TableListInfoView,
            columnDataSet: MutableList<ColumnData>?
        ) {
            tableListInfoView.setData(columnDataSet)
        }
    }

}
