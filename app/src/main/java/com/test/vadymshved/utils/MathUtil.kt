package com.test.vadymshved.utils

object MathUtil {
    fun calculatePerformanceData(
        firstValue: Float,
        comparingValue: Float
    ) : Float {
        return ((comparingValue.div(firstValue)) - 1) * 100
    }
}