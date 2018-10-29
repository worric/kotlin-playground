package me.worric.kotlinplayground.domain.commands

import me.worric.kotlinplayground.domain.datasource.ForecastProvider
import me.worric.kotlinplayground.domain.model.Forecast

class RequestDayForecastCommand(
        val id: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)

}