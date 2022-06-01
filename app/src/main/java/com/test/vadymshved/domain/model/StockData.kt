package com.test.vadymshved.domain.model

data class StockData(
    val name: String,
    val values: List<ChartData>
)