package me.worric.kotlinplayground.domain.datasource

import me.worric.kotlinplayground.domain.model.Forecast
import me.worric.kotlinplayground.domain.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?
}