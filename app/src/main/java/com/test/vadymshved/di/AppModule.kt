package com.test.vadymshved.di

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.gson.Gson
import com.test.vadymshved.data.repository.StockRepositoryImpl
import com.test.vadymshved.domain.repository.StockRepository
import com.test.vadymshved.utils.ResourcesHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [AppModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideValueFormatter() = object : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            if (axis is XAxis) {
                return ResourcesHelper.convertFloatTimestampToDate(value)
            }
            return super.getAxisLabel(value, axis)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {
        @Binds
        abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl): StockRepository
    }
}