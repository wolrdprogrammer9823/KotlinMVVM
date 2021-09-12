package com.wolfsea.kotlinmvvm
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wolfsea.kotlinmvvm.adapter.CardABindingAdapter
import com.wolfsea.kotlinmvvm.bean.CardBean
import com.wolfsea.kotlinmvvm.callback.CardItemHelper
import com.wolfsea.kotlinmvvm.callback.CardTouchCallback
import com.wolfsea.kotlinmvvm.databinding.ActivityFeatureBinding
import com.wolfsea.kotlinmvvm.layoutmanager.CardLayoutManager
import kotlinx.android.synthetic.main.activity_feature.*

class FeatureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeatureBinding
    private lateinit var mCardAdapter: CardABindingAdapter
    private lateinit var mItemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityFeatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()

        initView()
    }

    /*
    * 初始化view
    * */
    private fun initView() {

        card_recycler_view.apply {
            layoutManager = CardLayoutManager()
            adapter = mCardAdapter
        }

        mItemTouchHelper.attachToRecyclerView(card_recycler_view)
    }

    /*
    * 初始化数据
    * */
    private fun initData() {

        mCardAdapter = CardABindingAdapter(this)
        mCardAdapter.items.addAll(obtainDataList())

        mItemTouchHelper = ItemTouchHelper(CardTouchCallback(mCardItemHelper))
    }


    /*
    * 创建数据集
    * */
    private fun obtainDataList(): MutableList<CardBean> {

        val dataList = mutableListOf<CardBean>()

        dataList.add(CardBean("高安", R.drawable.ic_gaoan))
        dataList.add(CardBean("萍乡", R.drawable.ic_pingxiang))
        dataList.add(CardBean("吉安", R.drawable.ic_jian))
        dataList.add(CardBean("九江", R.drawable.ic_jiujiang))
        dataList.add(CardBean("南昌", R.drawable.ic_nanc))
        dataList.add(CardBean("上饶", R.drawable.ic_shangrao))
        dataList.add(CardBean("宜春", R.drawable.ic_yichun))
        dataList.add(CardBean("鹰潭", R.drawable.ic_yingtan))
        dataList.add(CardBean("抚州", R.drawable.ic_fuzhou))

        return dataList
    }

    private val mCardItemHelper = object : CardItemHelper {
        override fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int) {
            mCardAdapter.run {
                items.removeAt(holder.layoutPosition)
                notifyItemRemoved(holder.layoutPosition)
            }
        }
    }

}