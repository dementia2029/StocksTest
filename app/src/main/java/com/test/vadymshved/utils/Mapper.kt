package com.test.vadymshved.utils

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.test.vadymshved.data.source.local.file.model.QuoteSymbol
import com.test.vadymshved.domain.model.ChartData
import com.test.vadymshved.domain.model.StockData

fun QuoteSymbol.toStockData(): StockData {
    val chartData = arrayListOf<ChartData>()
    closures[0].let { firstValue ->
        closures.forEachIndexed { index, closed ->
            try {
                chartData.add(
                    ChartData(
                        timestamps[index],
                        MathUtil.calculatePerformanceData(firstValue, closed)
                    )
                )
            } catch (e : IndexOutOfBoundsException){ }
        }
    }

    return StockData(symbol, chartData)
}

fun List<StockData>.toLineData(colorsMap: HashMap<String, Int>): LineData{
    val dataSets = arrayListOf<ILineDataSet>()
    forEach { stock ->
        val entries = arrayListOf<Entry>()
        stock.values.forEach { value ->
            entries.add(Entry(value.timestamp.toFloat(), value.performanceValue))
        }
        LineDataSet(entries, stock.name).apply {
            colorsMap[stock.name]?.let {
                color = it
            } ?: kotlin.run {
                ResourcesHelper.getRandomColor().also {
                    colorsMap[stock.name] = it
                    color = it
                }
            }

            setDrawCircles(false)
            setDrawValues(false)
            dataSets.add(this)
        }
    }
    return LineData(dataSets)
}