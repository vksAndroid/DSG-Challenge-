package countryinfo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import countryinfo.app.ui.screens.HomeScreen
import countryinfo.app.ui.theme.CountryInfoTheme
import countryinfo.app.utils.LocationPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // Ask location permission at the time of launch
            LocationPermission()

            CountryInfoTheme {
                HomeScreen()
            }
        }
    }
}

object Graph {
    const val CountryDetail = "country_details"
}
