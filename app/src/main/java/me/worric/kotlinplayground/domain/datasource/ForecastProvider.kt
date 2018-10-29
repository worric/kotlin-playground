package me.worric.kotlinplayground.domain.datasource

import me.worric.kotlinplayground.data.db.ForecastDb
import me.worric.kotlinplayground.data.server.ForecastServer
import me.worric.kotlinplayground.domain.model.ForecastList
import me.worric.kotlinplayground.extensions.firstResult

class ForecastProvider(private val sources: List<ForecastDataSource> = SOURCES) {

    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES = listOf(ForecastDb(), ForecastServer())
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        val res = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (res != null && res.size >= days) res else null
    }

    fun requestForecast(id: Long) = requestToSources { it.requestDayForecast(id) }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    private fun<T: Any> requestToSources(f: (ForecastDataSource) -> T?): T = sources.firstResult { f(it) }

}