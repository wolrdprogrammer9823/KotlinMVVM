package com.wolfsea.kotlinmvvm.layoutmanager
import androidx.recyclerview.widget.RecyclerView

/**
 *@desc  自定义LayoutManager
 *@author liuliheng
 *@time 2021/9/12  16:56
 **/
class CustomLayoutManager : RecyclerView.LayoutManager() {

    private var mSumDy = 0
    private var mTotalHeight = 0

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

        super.onLayoutChildren(recycler, state)

        //在布局之前,将所有的子view先detach掉,放入到scrap缓存中.
        detachAndScrapAttachedViews(recycler!!)

        var offsetY = 0

        for (i in 0.until(itemCount)) {

            //添加view
            val child = recycler.getViewForPosition(i)
            addView(child)
            //测量view宽和高
            measureChildWithMargins(child, 0, 0)

            //重新获取宽和高并排列布局
            val mWidth = getDecoratedMeasuredWidth(child)
            val mHeight = getDecoratedMeasuredHeight(child)

            layoutDecorated(child, 0, offsetY, mWidth, offsetY + mHeight)

            offsetY += mHeight
        }

        //如果所有子view的高度没有填充满recyclerview的高度,则将高度设置recyclerview的高度.
        mTotalHeight = offsetY.coerceAtLeast(getVerticalSpace())
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {

        var travel = dy

        if (mSumDy + dy < 0) {

            //如果滑动到顶部
            travel = -mSumDy
        } else if (mSumDy + dy > mTotalHeight - getVerticalSpace()) {

            //滑动到底部
            travel = mTotalHeight - getVerticalSpace() - mSumDy
        }

        mSumDy += travel

        //平移容器内的item
        offsetChildrenVertical(-travel)

        return dy
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun canScrollVertically(): Boolean = true

    private fun getVerticalSpace(): Int = height - paddingTop - paddingBottom
}