package me.worric.kotlinplayground.domain.commands

import me.worric.kotlinplayground.data.ForecastRequest
import me.worric.kotlinplayground.domain.mappers.ForecastDataMapper
import me.worric.kotlinplayground.domain.model.ForecastList

class RequestForecastCommand(val zipCode: String) : Command<ForecastList> {

    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }

}