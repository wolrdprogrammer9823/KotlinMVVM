package com.wolfsea.kotlinmvvm.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 *@desc  公共的BindigAdapter
 *@author liuliheng
 *@time 2021/9/4  16:13
 **/
abstract class BaseBindingAdapter<M, B : ViewBinding>(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val baseContext = context

    var items: ObservableArrayList<M> = ObservableArrayList()

    //高阶函数实现item事件点击
    var onViewClick: ((position: Int) -> Unit)? = null

    private val listChangedCallback by lazy {
        ListChangedCallback()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        items.addOnListChangedCallback(listChangedCallback)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        items.removeOnListChangedCallback(listChangedCallback)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding: B = DataBindingUtil.inflate(
            LayoutInflater.from(baseContext),
            getLayoutResId(viewType),
            parent,
            false
        )

        return BaseBindingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding: B? = DataBindingUtil.findBinding(holder.itemView)
        /*holder.itemView.setOnClickListener {
            onViewClick?.invoke(position)
        }*/
        onBindItem(binding, items[position], holder.layoutPosition)
    }

    override fun getItemCount(): Int = items.size

    @LayoutRes
    abstract fun getLayoutResId(viewType: Int): Int

    abstract fun onBindItem(binding: B?, item: M?, position: Int)

    fun onChanged(newItems: ObservableArrayList<M>) {
        resetItems(newItems)
        notifyDataSetChanged()
    }

    fun onItemRangeChanged(newItems: ObservableArrayList<M>, positionStart: Int, itemCount: Int) {
        resetItems(newItems)
        notifyItemRangeChanged(positionStart, itemCount)
    }

    fun onItemRangedInserted(newItems: ObservableArrayList<M>, positionStart: Int, itemCount: Int) {
        resetItems(newItems)
        notifyItemRangeInserted(positionStart, itemCount)
    }

    fun onItemChangeMoved(
        newItems: ObservableArrayList<M>,
        positionStart: Int,
        positionEnd: Int
    ) {
        resetItems(newItems)
        notifyItemMoved(positionStart, positionEnd)
    }

    fun onItemChangeRemoved(newItems: ObservableArrayList<M>, positionStart: Int, itemCount: Int) {
        resetItems(newItems)
        notifyItemRangeRemoved(positionStart, itemCount)
    }

    private fun resetItems(newItems: ObservableArrayList<M>) {
        this.items = newItems
    }

    /*
    * 列表改变回调
    * */
    private inner class ListChangedCallback : ObservableList.OnListChangedCallback<ObservableArrayList<M>>() {
        override fun onChanged(sender: ObservableArrayList<M>?) {
            sender?.let {
                this@BaseBindingAdapter.onChanged(it)
            }
        }

        override fun onItemRangeChanged(
            sender: ObservableArrayList<M>?,
            positionStart: Int,
            itemCount: Int
        ) {
            if (sender != null) {
                this@BaseBindingAdapter.onItemRangeChanged(sender, positionStart, itemCount)
            }
        }

        override fun onItemRangeInserted(
            sender: ObservableArrayList<M>?,
            positionStart: Int,
            itemCount: Int
        ) {
            if (sender != null) {
                this@BaseBindingAdapter.onItemRangedInserted(sender, positionStart, itemCount)
            }
        }

        override fun onItemRangeMoved(
            sender: ObservableArrayList<M>?,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
            if (sender != null) {
                this@BaseBindingAdapter.onItemChangeMoved(
                    sender,
                    fromPosition,
                    toPosition
                )
            }
        }

        override fun onItemRangeRemoved(
            sender: ObservableArrayList<M>?,
            positionStart: Int,
            itemCount: Int
        ) {
            if (sender != null) {
                this@BaseBindingAdapter.onItemChangeRemoved(sender, positionStart, itemCount)
            }
        }
    }

    /*
    * ViewHolder
    * */
    class BaseBindingViewHolder(view: View) : RecyclerView.ViewHolder(view)

}