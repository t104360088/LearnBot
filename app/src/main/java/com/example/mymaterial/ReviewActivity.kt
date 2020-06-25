package com.example.mymaterial

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.activity_review.*
import java.util.ArrayList

class ReviewActivity : AppCompatActivity() {

    private lateinit var tfRegular: Typeface
    private lateinit var tfLight: Typeface

    protected val parties = arrayOf(
        "數學",
        "天文",
        "化學",
        "美術"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        tfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf")
        tfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf")

        title = "成果評量"

        chart.setUsePercentValues(true)
        chart.getDescription().setEnabled(false)
        chart.setExtraOffsets(5f, 10f, 5f, 5f)

        chart.setDragDecelerationFrictionCoef(0.95f)

        chart.setCenterText("學習比例")
        chart.setCenterTextSize(22f)

        chart.setDrawHoleEnabled(true)
        chart.setHoleColor(Color.WHITE)

        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)

        chart.setHoleRadius(58f)
        chart.setTransparentCircleRadius(61f)

        chart.setDrawCenterText(true)

        chart.setRotationAngle(0f)
        chart.setRotationEnabled(true)
        chart.setHighlightPerTapEnabled(true)


        chart.animateY(1400, Easing.EaseInOutQuad)

        val l = chart.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.setDrawInside(false)
        l.setXEntrySpace(7f)
        l.setYEntrySpace(0f)
        l.setYOffset(0f)

        // entry label styling
        chart.setEntryLabelColor(Color.BLACK)
        chart.setEntryLabelTypeface(tfRegular)
        chart.setEntryLabelTextSize(18f)
        setData(4, 10f)
    }

    private fun setData(count: Int, range: Float) {
        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            entries.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat(),
                    parties[i % parties.size],
                    resources.getDrawable(R.drawable.ic_mic)
                )
            )
        }

        val dataSet = PieDataSet(entries, "課程項目")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextSize(18f)
        data.setValueTextColor(Color.BLACK)
        data.setValueTypeface(tfLight)
        chart.data = data

        chart.highlightValues(null)
        chart.invalidate()
    }
}
