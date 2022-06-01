package com.test.vadymshved.data.source.local.file.model

data class QuoteSymbol(
    val closures: List<Float>,
    val highs: List<Float>,
    val lows: List<Float>,
    val opens: List<Float>,
    val symbol: String,
    val timestamps: List<Long>,
    val volumes: List<Long>
)