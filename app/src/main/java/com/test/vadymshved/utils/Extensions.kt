package com.test.vadymshved.utils

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData

fun LineChart.refreshData(lineData: LineData){
    data = lineData
    invalidate()
}