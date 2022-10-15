package countryinfo.app.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import countryinfo.app.Graph
import countryinfo.app.ui.screens.detail.CountryMapScreen
import countryinfo.app.ui.screens.detail.DetailOverViewTab
import countryinfo.app.ui.screens.search.HomeSavedTab
import countryinfo.app.ui.screens.search.HomeSearchTab
import countryinfo.app.uicomponents.DefaultSnackBar
import countryinfo.app.uicomponents.main.BottomBarConditional
import countryinfo.app.uicomponents.main.TopBarConditional
import countryinfo.app.utils.ScreenOptions
import countryinfo.app.utils.networkconnection.ConnectionState
import countryinfo.app.utils.networkconnection.connectivityState
import countryinfo.app.utils.tabs.BottomTab
import countryinfo.app.vm.CountryListVm
import countryinfo.app.R


@Composable
fun HomeScreen() {

    val navHostController = rememberNavController()

    val viewModel: CountryListVm = hiltViewModel()

    var isFav by rememberSaveable { mutableStateOf(viewModel.isFav) }

    var clickedTab by rememberSaveable { mutableStateOf(viewModel.selectedTab) }

    var title = rememberSaveable {
        viewModel.title
    }

    val getSaveScreen = viewModel.getSavedScreen().collectAsState()

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    val scaffoldState = rememberScaffoldState()

    val errorState = viewModel.observeErrorState().collectAsState()

    val noInterNetMessage =stringResource(id = R.string.there_is_no_internet)

    LaunchedEffect(key1 = errorState.value, key2 = isConnected) {

        if (!isConnected) {
            scaffoldState.snackbarHostState.showSnackbar(noInterNetMessage)
        } else if (errorState.value.isNotEmpty()) {
            scaffoldState.snackbarHostState.showSnackbar(errorState.value)
        }
    }

    Scaffold(
        topBar = {
            TopBarConditional(
                title = title.value,
                bar = getSaveScreen.value,
                isSaved = isFav.value,
                onSavePress = {
                    if (isFav.value) {
                        viewModel.removeFavourite(viewModel.observeCountryData().value)
                    } else {
                        viewModel.addFavourite(viewModel.observeCountryData().value)
                    }

                }) {
                viewModel.setSavedScreen(ScreenOptions.SearchScreen)
                navHostController.navigateUp()
            }
        },
        bottomBar = {
            BottomBarConditional(navController = navHostController, bar = getSaveScreen.value)
        },
        scaffoldState = scaffoldState,


        //   DefaultSnackBar(snackbarHostState = scaffoldState.snackbarHostState, {})
//            if (isConnected.not()) {
//                Snackbar(
//                    action = {},
//                    modifier = Modifier
//                        .padding(8.dp)
//                ) {
//                    Text(text = stringResource(R.string.there_is_no_internet))
//                }
//            }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = it.calculateBottomPadding()
                )
        ) {



            Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {

                getSaveScreen.value.let {

                    val route = if (it == ScreenOptions.SearchScreen) {

                        if (clickedTab.value == "search")
                            BottomTab.TabSearch.route
                        else
                            BottomTab.TabSaved.route
                    } else
                        BottomTab.TabOverview.route

                    SearchNavigationGraph(
                        navController = navHostController, viewModel = viewModel, route
                    )

                    DefaultSnackBar(
                        snackbarHostState = scaffoldState.snackbarHostState,
                        onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
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
    route: String
) {

    NavHost(
        navController = navController,
        startDestination = route
    ) {

        composable(BottomTab.TabSearch.route) {
            HomeSearchTab(
                navController = navController,
                viewModel = viewModel
            )
            viewModel.selectedTab.value = "search"
        }
        composable(BottomTab.TabSaved.route) {
            HomeSavedTab(
                navController = navController,
                viewModel = viewModel
            )
            viewModel.selectedTab.value = "saved"

        }

        composable(
            route = BottomTab.TabOverview.route
        ) {
            DetailOverViewTab(viewModel = viewModel)

        }
        composable(BottomTab.TabMap.route) {
            CountryMapScreen(
                viewModel = viewModel
            )

        }

        composable(route = Graph.CountryDetail) {
            CountryDetailsScreen(viewModel)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowCountrySearchScreenPreview() {
    //HomeScreen( null)
}