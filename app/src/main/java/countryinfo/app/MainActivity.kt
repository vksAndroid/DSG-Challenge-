package countryinfo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import countryinfo.app.presentation.screens.home.HomeScreen
import countryinfo.app.theme.CountryInfoTheme
import countryinfo.app.utils.GetAppPermissions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // Ask App permission at the time of launch
            GetAppPermissions()
            CountryInfoTheme {
                // Call HomeScreen/Countries List screen after Launch MainActivity
                HomeScreen()
            }
        }
    }
}
