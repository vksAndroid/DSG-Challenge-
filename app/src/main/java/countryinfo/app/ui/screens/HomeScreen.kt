package countryinfo.app.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import countryinfo.app.utils.ScreenOptions
import countryinfo.app.utils.networkconnection.ConnectionState
import countryinfo.app.utils.networkconnection.connectivityState
import countryinfo.app.utils.tabs.BottomTab
import countryinfo.app.vm.CountryListVm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun HomeScreen() {

    val navHostController = rememberNavController()

    val viewModel : CountryListVm = hiltViewModel()


    val progressBar = remember { mutableStateOf(false) }
    val countryList = viewModel.observeCountryList().collectAsState()
    val searchList = viewModel.observeSearchCountryList().collectAsState()

    var title = rememberSaveable {
        viewModel.title
    }

    val getSaveScreen = viewModel.getSavedScreen().collectAsState()

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    Log.d("Search List", Gson().toJson(searchList))

    if (searchList.value.isEmpty() && countryList.value.isEmpty()) {
        viewModel.getCountryList()
    }

    Scaffold(topBar = {
        getSaveScreen.value.let {

            TopBarConditional(title = title.value, bar = it, onSavePress ={} ){
                viewModel.setSavedScreen(ScreenOptions.SearchScreen)
                navHostController.navigateUp()
            }
        }
    }, bottomBar = {
            getSaveScreen.value.let {
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

            getSaveScreen.value.let {
                
               val route =  if(it == ScreenOptions.SearchScreen)
                   BottomTab.TabSearch.route
                else
                    BottomTab.TabOverview.route

                SearchNavigationGraph(navController = navHostController
                    , viewModel = viewModel,route
                )
            }


        }

        BackHandler(enabled = true) {
            viewModel.title.value = "Search"
            viewModel.setSavedScreen(ScreenOptions.SearchScreen)
            navHostController.navigateUp()
        }
    }
}


@Composable
fun SearchNavigationGraph(
    navController: NavHostController,
    viewModel: CountryListVm,
    route : String) {

    NavHost(navController = navController,
        startDestination =route) {

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
                viewModel = viewModel,
                mFusedLocationClient =  LocationServices.getFusedLocationProviderClient(
                    LocalContext.current)
            )

        }

        composable(route = Graph.CountryDetail,) {
                        CountryDetailsScreen(viewModel)


        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowCountrySearchScreenPreview() {
    //HomeScreen( null)
}