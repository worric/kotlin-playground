package me.worric.kotlinplayground.domain.commands

import me.worric.kotlinplayground.data.server.ForecastRequest
import me.worric.kotlinplayground.domain.mappers.ForecastDataMapper
import me.worric.kotlinplayground.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: Long) : Command<ForecastList> {

    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(zipCode, forecastRequest.execute())
    }

}