package com.wolfsea.kotlinmvvm.defineview
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.widget.AppCompatTextView
import com.wolfsea.kotlinmvvm.R
import com.wolfsea.kotlinmvvm.bean.ColumnData
import java.text.DecimalFormat
import kotlin.properties.Delegates

/**
 *@desc
 *@author liuliheng
 *@time 2021/8/18  0:20
 **/
class TableListInfoView(context: Context, attributeSet: AttributeSet) : RelativeLayout(context, attributeSet) {

    private var headBg by Delegates.notNull<Int>()
    private var borderWidth by Delegates.notNull<Float>()
    private var headerHeight by Delegates.notNull<Float>()
    private var contentHeight by Delegates.notNull<Float>()

    private lateinit var mContext: Context
    private var dataSet: MutableList<ColumnData>? = null

    init {
        init(context, attributeSet)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
    }


    private fun init(context: Context, attributeSet: AttributeSet) {

        mContext = context

        context.obtainStyledAttributes(attributeSet, R.styleable.LayerInfoTableView).apply {

            headBg = getColor(
                R.styleable.LayerInfoTableView_head_bg,
                resources.getColor(R.color.teal_200)
            )

            borderWidth = getDimension(
                R.styleable.LayerInfoTableView_border,
                1F)

            headerHeight = getDimension(
                R.styleable.LayerInfoTableView_head_height,
                100F)

            contentHeight = getDimension(
                R.styleable.LayerInfoTableView_content_height,
                100F)

            recycle()
        }
    }

    /**
     *@desc  创建表格行
     *@author:liuliheng
     *@time: 2021/8/24 22:01
    **/
    private fun makeTableRows(vararg otherRows: String): TableRow {

        val tableRow = TableRow(mContext)
        val cellWidth = width.div(otherRows.size)

        for (i in 0.until(otherRows.size)) {

            val cellView = makeCellView(cellWidth, otherRows[i])
            tableRow.addView(cellView, i)
            val layoutParams = cellView.layoutParams as TableRow.LayoutParams
            layoutParams.weight = 1.0F
            if (i > 0) {

                layoutParams.leftMargin = resources.getDimension(R.dimen.dp_10).toInt()
            }
            cellView.layoutParams = layoutParams
        }

        return tableRow
    }

    /**
     *@desc 创建表格的item
     *@author: liuliheng
     *@time: 2021/8/24 21:49
    **/
    private fun makeCellView(cellWidth: Int, name: String): AppCompatTextView {
        val cellView = AppCompatTextView(mContext)
        cellView.apply {
             //isSingleLine = true
             isSelected = true
             text = name
             width = 0
             height = contentHeight.toInt()
             setBackgroundResource(R.drawable.list_item_background)
             setTextColor(Color.BLACK)
             gravity = Gravity.CENTER
             //ellipsize = TextUtils.TruncateAt.MARQUEE
        }
        return cellView
    }

    /**
     *@desc 初始化表格数据
     *@author:liuliheng
     *@time: 2021/8/24 22:46
    **/
    private fun initTableContent(columnDataSet: MutableList<ColumnData>?) {

        LayoutInflater.from(mContext).inflate(R.layout.table_list_item, this)

        val tableContent = this.findViewById<TableLayout>(R.id.table_list_content)
        tableContent.isStretchAllColumns = true
        tableContent.removeAllViews()

        for (columnData in columnDataSet!!) {

            val tableRow = makeTableRows(
                columnData.name,
                float2Str(columnData.dlRate),
                float2Str(columnData.ulRate)
            )

            val layoutParams = TableLayout.LayoutParams(WARP_CONTENT, MATCH_PARENT)
            layoutParams.topMargin = mContext.resources.getDimension(R.dimen.dp_10).toInt()
            tableContent.addView(tableRow, layoutParams)
        }
    }

    /**
     *@desc 设置数据
     *@author:liuliheng
     *@time: 2021/8/24 22:47
    **/
    fun setData(columnDataSet: MutableList<ColumnData>?) {

        dataSet = columnDataSet
        initTableContent(dataSet)
    }

    companion object {

        const val WARP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT
        const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT

        fun float2Str(f: Float): String {
            val decimalFormat = DecimalFormat("#.00")
            return decimalFormat.format(f)
        }
    }

}