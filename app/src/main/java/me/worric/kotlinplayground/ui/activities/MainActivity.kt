package me.worric.kotlinplayground.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.worric.kotlinplayground.R
import me.worric.kotlinplayground.data.Person
import me.worric.kotlinplayground.domain.commands.RequestForecastCommand
import me.worric.kotlinplayground.domain.model.ForecastList
import me.worric.kotlinplayground.extensions.Preference
import me.worric.kotlinplayground.ui.adapters.ForecastListAdapter
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

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

    /* It doesn't matter much leaking the context for 1-2 seconds if it sacrifices readability */
    private fun loadForecast() = GlobalScope.launch(Dispatchers.Main) {
        val result = async { RequestForecastCommand(zipCode).execute() }
        updateUI(result.await())
    }

    private fun updateUI(weekForecast: ForecastList) {
        val adapter = ForecastListAdapter(weekForecast) {
            startActivity<DetailActivity>(DetailActivity.ID to it.id,
                    DetailActivity.CITY_NAME to weekForecast.city)
        }
        forecastList.adapter = adapter
        toolbarTitle = "${weekForecast.city} (${weekForecast.country})"
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
