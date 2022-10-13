package countryinfo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import countryinfo.app.ui.screens.HomeScreen
import countryinfo.app.ui.theme.CountryInfoTheme
import countryinfo.app.vm.CountryListVm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContent {


            CountryInfoTheme {
                HomeScreen()
            }
        }
    }
}

object Graph {
    const val CountryDetail = "country_details"
}
