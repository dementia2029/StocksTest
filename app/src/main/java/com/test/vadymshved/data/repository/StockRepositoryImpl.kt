package com.test.vadymshved.data.repository

import com.test.vadymshved.R
import com.test.vadymshved.data.source.local.file.model.responses.getstocks.GetStocksResponse
import com.test.vadymshved.data.source.local.file.model.QuoteSymbol
import com.test.vadymshved.domain.model.StockPeriod
import com.test.vadymshved.domain.repository.StockRepository
import com.test.vadymshved.utils.ResourcesHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val resourcesHelper: ResourcesHelper
) : StockRepository {
    override fun get(
        stockPeriod: StockPeriod,
        dispatcher: CoroutineDispatcher,
    ): Flow<Result<List<QuoteSymbol>>> = flow {
        val result: Result<GetStocksResponse> = resourcesHelper.readRawFileAndConvertToObject(
            when (stockPeriod){
                StockPeriod.Monthly -> R.raw.month
                StockPeriod.Weekly -> R.raw.week
            },
            Dispatchers.IO,
            GetStocksResponse::class.java
        )
        if (result.isSuccess){
            emit(Result.success(result.getOrThrow().content.quoteSymbols))
        } else {
            emit(Result.failure(result.exceptionOrNull() ?: Throwable()))
        }
    }
}