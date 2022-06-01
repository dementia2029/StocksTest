package com.test.vadymshved.features.chart

import androidx.lifecycle.*
import com.github.mikephil.charting.data.LineData
import com.test.vadymshved.domain.model.StockPeriod
import com.test.vadymshved.domain.repository.StockRepository
import com.test.vadymshved.utils.Constants
import com.test.vadymshved.utils.toStockData
import com.test.vadymshved.utils.toLineData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val stockRepository: StockRepository
) : ViewModel() {

    private val colorsMap: HashMap<String, Int> = HashMap()

    val selectedPeriod: MutableLiveData<StockPeriod> =
        savedStateHandle.getLiveData(Constants.TAG_STOCK_PERIOD, Constants.defaultStockPeriod)

    val switcherIsSelected: LiveData<Boolean> = selectedPeriod.map {
        when (it) {
            StockPeriod.Monthly -> true
            else -> false
        }
    }

    val filteredData: LiveData<LineData> =
        selectedPeriod.switchMap { period ->
            stockRepository.get(period, Dispatchers.IO)
                .map { result ->
                    result.getOrNull()?.map { it.toStockData() } ?: emptyList()
                }
                .map { list ->
                    list.toLineData(colorsMap)
                }
                .asLiveData(Dispatchers.Main)
        }

    fun switcherIsChecked(enabled: Boolean) {
        savedStateHandle[Constants.TAG_STOCK_PERIOD] =
            if (enabled) StockPeriod.Monthly
            else StockPeriod.Weekly
    }
}