package com.wolfsea.kotlinmvvm.layoutmanager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

/**
 *@desc  卡片布局管理器
 *@author liuliheng
 *@time 2021/9/12  23:43
 **/
class CardLayoutManager(
    showViewMax: Int = 5,
    sectionScale: Float = 0.075F,
    sectionTranslation: Float = 10F
) : RecyclerView.LayoutManager() {


    private var mShowViewMax = showViewMax
    private var mSectionScale = sectionScale
    private var mSectionTranslation = sectionTranslation

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

        super.onLayoutChildren(recycler, state)

        if (itemCount <= 0 || state?.isPreLayout!!) {
            return
        }

        //先移除所有view
        detachAndScrapAttachedViews(recycler!!)
        val viewCount = min(itemCount, mShowViewMax)

        for (i in (viewCount - 1).downTo(0)) {
            //获取view,添加view,测量view.
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)

            //获取宽高
            val decoWidth = getDecoratedMeasuredWidth(view)
            val decoHeight = getDecoratedMeasuredHeight(view)
            //view在Recyclerview中的剩余宽度
            val widthSpace = width - decoWidth

            //排列布局view,水平居中,靠上.
            layoutDecoratedWithMargins(
                view,
                widthSpace.div(2),
                0,
                widthSpace.div(2).plus(decoWidth),
                decoHeight
            )

            //对view进行动画操作
            view.run {
                scaleX = getScaleX(position = i)
                scaleY = getScaleY(position = i)
                translationX = getTranslationX(position = i)
                translationY = getTranslationY(position = i)
            }
        }
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    private fun getScaleX(position: Int): Float = 1F - position * mSectionScale

    private fun getScaleY(position: Int): Float = 1F

    private fun getTranslationX(position: Int) = 0F

    private fun getTranslationY(position: Int) = position * mSectionTranslation
}