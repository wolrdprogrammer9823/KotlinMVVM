package com.wolfsea.kotlinmvvm.layoutmanager
import android.graphics.Rect
import android.util.SparseArray
import android.util.SparseBooleanArray
import androidx.recyclerview.widget.RecyclerView

/**
 *@desc  自定义LayoutManager
 *@author liuliheng
 *@time 2021/9/24  0:00
 **/
class DefineLayoutManager : RecyclerView.LayoutManager() {

    private var totalHeight = 0

    private var verticalScrollOffset = 0

    private val itemStates = SparseBooleanArray()

    private val allItemRect = SparseArray<Rect>()

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

        super.onLayoutChildren(recycler, state)

        if (itemCount <= 0 || state?.isPreLayout!!) {

            return
        }

        //把所有的View先从RecyclerView中detach掉,然后标记为"scrap"状态,表示这些view处于可重用状态,实际只是把这些
        //view放在了Recycler的一个集合中.
        detachAndScrapAttachedViews(recycler!!)
        calculateChildrenSite(recycler)
        //回收并显示view
        recyclerAndFillView(recycler, state)
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {

        //每次滑动时把所有的view先释放掉,
        detachAndScrapAttachedViews(recycler!!)

        var travel = dy
        if (verticalScrollOffset + dy < 0) {
            //滑动到顶部
            travel = -verticalScrollOffset
        } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {
            //滑动到底部
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset
        }

        //通知view在指定方向上移动指定距离
        offsetChildrenVertical(-travel)
        //回收并显示view
        recyclerAndFillView(recycler, state)
        //将竖直方向的偏移量+travel
        verticalScrollOffset += travel
        return travel
    }


    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

    override fun canScrollVertically(): Boolean = true

    /*
    * 计算并保存每个item的位置
    * */
    private fun calculateChildrenSite(recycler: RecyclerView.Recycler?) {

        totalHeight = 0

        for (i in 0.until(itemCount)) {

            val child = recycler?.getViewForPosition(i)
            addView(child)
            measureChildWithMargins(child!!, width.div(2), 0)
            val childWidth = getDecoratedMeasuredWidth(child)
            val childHeight = getDecoratedMeasuredHeight(child)
            val rect = Rect()
            //调整item的大小,去除ItemDecorator.
            calculateItemDecorationsForChild(child, rect)
            var newRect = allItemRect[i]
            if (newRect == null) {
                newRect = Rect()
            }
            if (i.rem(2) == 0) {
                //左边
                newRect.set(
                    0,
                    totalHeight,
                    width.div(2),
                    totalHeight + childHeight
                )
            } else {
                //右边
                newRect.set(
                    width.div(2),
                    totalHeight,
                    width,
                    totalHeight + childHeight
                )

                //需换行
                totalHeight += childHeight
            }

            //保存item的位置信息
            allItemRect.put(i, newRect)
            //由于之前调用过detachAndScrapAttachedViews(recycler: RecyclerView.Recycler?)方法,所以此时的
            //item都是不可见的.
            itemStates.put(i, false)
        }
    }

    private fun recyclerAndFillView(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

        if (itemCount <= 0 || state?.isPreLayout!!) {
            return
        }

        //当前scroll offset状态下显示的区域
        val displayRect = Rect(
            0,
            verticalScrollOffset,
            getHorizontalSpace(),
            verticalScrollOffset + getVerticalSpace()
        )

        //将滑出屏幕的Items回收到Recycler缓存中
        val childRect = Rect()
        for (i in 0.until(childCount)) {

            val child = recycler?.getViewForPosition(i)
            addView(child)

            childRect.left = getDecoratedLeft(child!!)
            childRect.top = getDecoratedTop(child)
            childRect.right = getDecoratedRight(child)
            childRect.bottom = getDecoratedBottom(child)

            //如果Item没有在显示区域,说明需要回收.
            if (!Rect.intersects(displayRect, childRect)) {

                removeAndRecycleView(child, recycler)
                //更新该View的状态为未依附
                itemStates.put(i, false)
            }
        }

        //重新显示需要出现在屏幕的子View
        for (i in 0.until(itemCount)) {
            //判断item的位置和当前显示区域是否重合
            if (Rect.intersects(displayRect, allItemRect[i])) {

                val child = recycler?.getViewForPosition(i)
                measureChildWithMargins(child!!, width.div(2), 0)
                addView(child)

                val rect = allItemRect[i]
                layoutDecoratedWithMargins(
                    child,
                    rect.left,
                    rect.top - verticalScrollOffset,
                    rect.right,
                    rect.bottom - verticalScrollOffset
                )

                itemStates.put(i, true)
            }
        }
    }

    /*
    * 获取垂直空间
    * */
    private fun getVerticalSpace(): Int = height - paddingBottom - paddingTop

    /*
    * 获取水平空间
    * */
    private fun getHorizontalSpace(): Int = width - paddingStart - paddingEnd

}