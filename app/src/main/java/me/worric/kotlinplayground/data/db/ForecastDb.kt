package me.worric.kotlinplayground.data.db

import me.worric.kotlinplayground.data.db.mappers.DbDataMapper
import me.worric.kotlinplayground.data.db.model.CityForecast
import me.worric.kotlinplayground.data.db.model.DayForecast
import me.worric.kotlinplayground.domain.datasource.ForecastDataSource
import me.worric.kotlinplayground.domain.model.ForecastList
import me.worric.kotlinplayground.extensions.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class ForecastDb(val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
                 val dataMapper: DbDataMapper = DbDataMapper())
    : ForecastDataSource {
    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {

        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }

        city?.let { dataMapper.convertToDomain(it) }
    }

    override fun requestDayForecast(id: Long) = forecastDbHelper.use {
        val forecast = select(DayForecastTable.NAME)
                .byId(id)
                .parseOpt { DayForecast(HashMap(it)) }

        forecast?.let { dataMapper.convertDayToDomain(forecast) }
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarArgArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarArgArray()) }
        }
    }
}