package com.wolfsea.kotlinmvvm.layoutmanager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.properties.Delegates

/**
 *@desc 旋转的LayoutManager
 *@author liuliheng
 *@time 2021/9/27  21:10
 **/
class TurntableLayoutManager(mRadius: Int, mEachAngle: Int) : RecyclerView.LayoutManager() {

    private val radius = mRadius
    private val eachAngle = mEachAngle

    private var mItemWidth by Delegates.notNull<Int>()
    private var mItemHeight by Delegates.notNull<Int>()

    private var mCircleMidX by Delegates.notNull<Int>()
    private var mCircleMidY by Delegates.notNull<Int>()

    private var mMoveAngle = 0F

    //布局所有的子view
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

        super.onLayoutChildren(recycler, state)
        //获得每个View的大小
        initItemSize(recycler)
        //根据屏幕中心获得圆的中心
        val screenMidX = width.div(2)
        val screenMidY = height.div(2)
        mCircleMidX = screenMidX
        mCircleMidY = screenMidY.plus(radius)
        //回收复用view
        detachAndScrapAttachedViews(recycler!!)
        //遍历子view重新排列
        for (i in 0.until(itemCount)) {

            val condition = abs(i.times(eachAngle)) < VISIBLE_ANGLE
            if (condition) {

                layoutViewByIndex(recycler, i)
            }
        }
    }

    //水平滚动
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {

        //计算角度
        var moveAngle = convertDxToAngle(dx)
        var actualDx = dx
        if (moveAngle + mMoveAngle > getMaxScrollAngle()) {

            moveAngle = getMaxScrollAngle() - mMoveAngle
            actualDx = convertAngle2Dx(moveAngle)
        } else if (moveAngle + mMoveAngle < 0) {

            moveAngle = -mMoveAngle
            actualDx = convertAngle2Dx(moveAngle)
        }
        mMoveAngle += moveAngle

        //移除回收view
        for (i in 0.until(itemCount)) {

            val child = getChildAt(i)
            if (child != null) {

                val position = getPosition(child)
                val currentAngle = position * eachAngle + mMoveAngle
                val condition = abs(currentAngle) >= VISIBLE_ANGLE
                if (condition) {

                    removeAndRecycleView(child, recycler!!)
                }
            }
        }

        detachAndScrapAttachedViews(recycler!!)

        //重新排列view
        for (i in 0.until(itemCount)) {

            val currentAngle = i * eachAngle - mMoveAngle
            val condition = abs(currentAngle) < VISIBLE_ANGLE
            if (condition) {

                layoutViewByIndex(recycler, i)
            }
        }

        return actualDx
    }

    //设置可以水平滚动

    override fun canScrollHorizontally(): Boolean = true
    //初始化布局参数
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

    //初始化item的大小
    private fun initItemSize(recycler: RecyclerView.Recycler?) {
        val child = recycler?.getViewForPosition(0)
        measureChildWithMargins(child!!, 0, 0)
        mItemWidth = getDecoratedMeasuredWidth(child)
        mItemHeight = getDecoratedMeasuredHeight(child)
        removeAndRecycleView(child, recycler)
    }

    //重新排列子view
    private fun layoutViewByIndex(recycler: RecyclerView.Recycler?, index: Int) {
        //根据当前子view的角度,并获取坐标
        val currentAngle = index.times(eachAngle).minus(mMoveAngle)
        val xToAdd = sin(2.times(PI).div(360).times(currentAngle)).times(radius).toInt()
        val yToMinus = cos(2.times(PI).div(360).times(currentAngle)).times(radius).toInt()
        val x = mCircleMidX.plus(xToAdd)
        val y = mCircleMidY.minus(yToMinus)
        //获取子view,添加并测量,重新布局并旋转.
        val child = recycler?.getViewForPosition(index)
        addView(child)
        measureChildWithMargins(child!!, 0, 0)
        layoutDecorated(
            child,
            x - mItemWidth / 2,
            y - mItemHeight / 2,
            x + mItemWidth / 2,
            y + mItemHeight / 2
        )
        child.rotation = currentAngle
    }

    private fun convertDxToAngle(dx: Int): Float = (360 * dx / (2 * PI * radius)).toFloat()

    private fun convertAngle2Dx(angle: Float): Int = ((2 * PI * radius * angle) / 360).toInt()

    private fun getMaxScrollAngle(): Int = (itemCount - 1) * eachAngle

    companion object {
        const val VISIBLE_ANGLE = 50
    }
}