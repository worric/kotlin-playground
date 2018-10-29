package me.worric.kotlinplayground.data.db.mappers

import me.worric.kotlinplayground.data.db.model.CityForecast
import me.worric.kotlinplayground.data.db.model.DayForecast
import me.worric.kotlinplayground.domain.model.Forecast
import me.worric.kotlinplayground.domain.model.ForecastList

class DbDataMapper {

    fun convertToDomain(forecast: CityForecast) = with(forecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id, city, country, daily)
    }

    fun convertDayToDomain(dayForecast: DayForecast) = with(dayForecast) {
        Forecast(_id, date, description, high, low, iconUrl)
    }

    fun convertFromDomain(forecast: ForecastList) = with(forecast) {
        val daily = dailyForecast.map { convertDayFromDomain(id, it) }
        CityForecast(id, city, country, daily)
    }

    private fun convertDayFromDomain(cityId: Long, forecast: Forecast) = with(forecast) {
        DayForecast(date, description, high, low, iconUrl, cityId)

    }

}
