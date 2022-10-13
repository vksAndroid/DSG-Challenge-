package countryinfo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.location.LocationServices
import countryinfo.app.ui.screens.CountryDetailsScreen
import countryinfo.app.ui.screens.CountrySearchScreen
import countryinfo.app.ui.theme.CountryInfoTheme
import countryinfo.app.vm.CountryListVm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CountryInfoTheme {
                SearchCountries()
            }
        }
    }
}

@Composable
private fun SearchCountries() {

    val navController = rememberNavController()
    RootNavigationGraph(navController)
}

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    val vm: CountryListVm = hiltViewModel()
    val countryItemId = stringResource(id = R.string.country_cca3_id)
    NavHost(
        navController = navController,
        route = Graph.Root, startDestination = Graph.CountryList
    ) {
        composable(Graph.CountryList) {
            CountrySearchScreen(navController, vm)
        }
        composable(
            route = "${Graph.CountryDetail}/{$countryItemId}",
            arguments = listOf(navArgument(countryItemId) {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.let {
                it.getString(countryItemId)
                    ?.let { it1 ->
                        CountryDetailsScreen(it1, vm, LocationServices.getFusedLocationProviderClient(
                            LocalContext.current)) {
                            navController.navigateUp()
                        }
                    }
            }
        }
    }
}

object Graph {
    const val Root = "root_graph"
    const val CountryList = "country_list"
    const val CountryDetail = "country_details"
}
