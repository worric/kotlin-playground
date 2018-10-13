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

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList =
            sources.firstResult { requestSource(it, days, zipCode) }

    private fun requestSource(source: ForecastDataSource, days: Int, zipCode: Long): ForecastList? {
        val res = source.requestForecastByZipCode(zipCode, todayTimeSpan())
        return if (res != null && res.size >= days) res else null
    }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

}