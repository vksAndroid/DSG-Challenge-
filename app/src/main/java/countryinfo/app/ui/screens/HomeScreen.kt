package countryinfo.app.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import countryinfo.app.Graph
import countryinfo.app.R
import countryinfo.app.ui.screens.detail.CountryMapScreen
import countryinfo.app.ui.screens.detail.DetailOverViewTab
import countryinfo.app.ui.screens.search.HomeSavedTab
 import countryinfo.app.ui.screens.search.HomeSearchTab
import countryinfo.app.uicomponents.main.BottomBarConditional
import countryinfo.app.uicomponents.main.TopBarConditional
import countryinfo.app.utils.WhichComponent
import countryinfo.app.utils.networkconnection.ConnectionState
import countryinfo.app.utils.networkconnection.connectivityState
import countryinfo.app.utils.tabs.BottomTab
import countryinfo.app.vm.CountryListVm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(viewModel: CountryListVm) {

    val navHostController = rememberNavController()

    val progressBar = remember { mutableStateOf(false) }
    val countryList = viewModel.observeCountryList().collectAsState()
    val searchList = viewModel.observeSearchCountryList().collectAsState()

    var title = rememberSaveable {
        viewModel.title
    }

    val getSaveComponent = viewModel.getSavedComponent().collectAsState()

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    Log.d("Search List", Gson().toJson(searchList))

    if (searchList.value.isEmpty() && countryList.value.isEmpty()) {
        viewModel.getCountryList()
    }

    Scaffold(topBar = {
        getSaveComponent.value.let {

            TopBarConditional(title = title.value, bar = it, onSavePress ={} ){
                viewModel.saveWhichComponent(WhichComponent.SearchScreen)
                navHostController.navigateUp()
            }
        }
    }, bottomBar =
        {
            getSaveComponent.value.let {
                BottomBarConditional(navController = navHostController, bar = it)

            }
        },


        snackbarHost = {
            if (isConnected.not()) {
                Snackbar(
                    action = {},
                    modifier = Modifier
                        .padding(8.dp)) {
                    Text(text = stringResource(R.string.there_is_no_internet))
                }
            }
        }) {

        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            SearchNavigationGraph(navController = navHostController
                , viewModel = viewModel
            )
        }

        BackHandler(enabled = true) {
            viewModel.title.value = "Search"
            viewModel.saveWhichComponent(WhichComponent.SearchScreen)
            navHostController.navigateUp()
        }
    }
}


@Composable
fun SearchNavigationGraph(
    navController: NavHostController,
    viewModel: CountryListVm) {

    NavHost(navController = navController,
        startDestination = BottomTab.TabSearch.route) {

        composable(BottomTab.TabSearch.route) {
            HomeSearchTab( navController = navController,
                viewModel = viewModel)

        }
        composable(BottomTab.TabSaved.route) {
            HomeSavedTab( navController = navController,
                viewModel = viewModel)
        }

        composable(route = BottomTab.TabOverview.route
        ) {
            DetailOverViewTab( viewModel = viewModel)

        }
        composable(BottomTab.TabMap.route) {
            CountryMapScreen(
                navController = navController,
                cca3 = "ISR",
                viewModel = viewModel,
                mFusedLocationClient =  LocationServices.getFusedLocationProviderClient(
                    LocalContext.current)
            )

        }

        composable(
            route = "${Graph.CountryDetail}/{cca3}",
            arguments = listOf(navArgument("cca3") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.let {
                it.getString("cca3")
                    ?.let { it1 ->
                        CountryDetailsScreen(viewModel)

                    }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowCountrySearchScreenPreview() {
    //HomeScreen( null)
}