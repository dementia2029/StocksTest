package com.test.vadymshved.domain.repository

import com.test.vadymshved.data.source.local.file.model.QuoteSymbol
import com.test.vadymshved.domain.model.StockPeriod
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    fun get(
        stockPeriod: StockPeriod,
        dispatcher: CoroutineDispatcher
    ) : Flow<Result<List<QuoteSymbol>>>
}