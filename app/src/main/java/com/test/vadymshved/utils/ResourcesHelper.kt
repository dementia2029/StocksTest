package com.test.vadymshved.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import com.google.gson.Gson
import com.test.vadymshved.R
import com.test.vadymshved.domain.model.StockPeriod
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ResourcesHelper @Inject constructor(
    @ApplicationContext
    val context: Context,
    private val gson: Gson
) {
    suspend fun <T> readRawFileAndConvertToObject(
        rawId: Int,
        dispatcher: CoroutineDispatcher,
        classOfObject: Class<T>
    ): Result<T> = withContext(dispatcher) {
        val inputStream = context.resources.openRawResource(rawId)
        val reader = BufferedReader(InputStreamReader(inputStream))
        convertFromJsonToObject(reader, classOfObject)
    }

    private suspend fun <T> convertFromJsonToObject(
        reader: Reader,
        classOfObject: Class<T>
    ): Result<T> = coroutineScope {
        try {
            val objectOfType = gson.fromJson(reader, classOfObject)
            Result.success(objectOfType)
        } catch (e: Exception) {
            Result.failure(Throwable())
        }
    }

    companion object {
        @SuppressLint("SimpleDateFormat")
        private val simpleDateFormat = SimpleDateFormat(Constants.DATE_PATTERN)

        fun getRandomColor(): Int {
            val rnd = Random()
            return Color.argb(
                Constants.ALPHA_CHANNEL,
                rnd.nextInt(Constants.S_RGB_RANGE),
                rnd.nextInt(Constants.S_RGB_RANGE),
                rnd.nextInt(Constants.S_RGB_RANGE)
            )
        }

        fun convertFloatTimestampToDate(time: Float): String {
            val timeLong = time.toLong()
            val date = Date(timeLong * 1000)
            val format = simpleDateFormat
            return format.format(date)
        }

        fun getTextForSelectedPeriod(
            stockPeriod: StockPeriod,
            context: Context
        ): String = when (stockPeriod) {
            StockPeriod.Weekly -> context.getString(R.string.stock_period_weekly)
            StockPeriod.Monthly -> context.getString(R.string.stock_period_yearly)
        }
    }
}