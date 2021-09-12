package com.wolfsea.kotlinmvvm.callback
import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 *@desc  卡片触摸回调
 *@author liuliheng
 *@time 2021/9/13  0:28
 **/
class CardTouchCallback(itemHelper: CardItemHelper?) :
    ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN
    ) {

    private val mItemHelper = itemHelper

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mItemHelper?.onSwiped(holder = viewHolder, direction)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

}