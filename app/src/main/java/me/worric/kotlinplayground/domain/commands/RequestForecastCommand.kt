package me.worric.kotlinplayground.domain.commands

import me.worric.kotlinplayground.domain.datasource.ForecastProvider
import me.worric.kotlinplayground.domain.model.ForecastList

class RequestForecastCommand(
        private val zipCode: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider())
    : Command<ForecastList> {

    companion object {
        val DAYS = 7
    }

    override fun execute(): ForecastList = forecastProvider.requestByZipCode(zipCode, DAYS)

}