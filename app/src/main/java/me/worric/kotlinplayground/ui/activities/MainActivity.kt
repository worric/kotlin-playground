package me.worric.kotlinplayground.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.worric.kotlinplayground.R
import me.worric.kotlinplayground.data.Person
import me.worric.kotlinplayground.domain.commands.RequestForecastCommand
import me.worric.kotlinplayground.extensions.DelegatesExt
import me.worric.kotlinplayground.extensions.Preference
import me.worric.kotlinplayground.ui.adapters.ForecastListAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), ToolbarManager {

    private val zipCode: Long by Preference(this, SettingsActivity.ZIP_CODE,
            SettingsActivity.DEFAULT_ZIP)
    override val toolBar: Toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)

        loadForecast()

        val person = Person(name = "John", surname = "Smith")
        toast(message = person.getNameFullName())
    }

    private fun loadForecast() = doAsync {
        val result = RequestForecastCommand(zipCode).execute()
        uiThread { _ ->
            // Simplify even further by using "it"; then we avoid left side of arrow
            forecastList.adapter = ForecastListAdapter(result) {
                startActivity<DetailActivity>(DetailActivity.ID to it.id,
                        DetailActivity.CITY_NAME to result.city)
            }
            toolbarTitle = "${result.city} (${result.country})"
            //                    toast(convertDate(it.date)) }
        }
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    fun toast(message: String,
              length: Int = Toast.LENGTH_SHORT,
              tag: String = MainActivity::class.java.simpleName) {
        Toast.makeText(this, "[$tag] $message", length).show()
    }

}
