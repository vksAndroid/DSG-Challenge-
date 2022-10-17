package countryinfo.app.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import countryinfo.app.R
import countryinfo.app.ui.screens.detail.CountryMapScreen
import countryinfo.app.ui.screens.detail.DetailOverViewTab
import countryinfo.app.ui.screens.search.DsgSearchTab
import countryinfo.app.ui.screens.search.HomeSavedTab
import countryinfo.app.ui.screens.search.HomeSearchTab
import countryinfo.app.uicomponents.DefaultSnackBar
import countryinfo.app.uicomponents.scaffold_comp.BottomBarConditional
import countryinfo.app.uicomponents.scaffold_comp.TopBarConditional
import countryinfo.app.utils.RouteCountryDetail
import countryinfo.app.utils.ScreenOptions
import countryinfo.app.utils.networkconnection.ConnectionState
import countryinfo.app.utils.networkconnection.connectivityState
import countryinfo.app.utils.tabs.BottomTab
import countryinfo.app.utils.titleSaved
import countryinfo.app.utils.titleSearch
import countryinfo.app.vm.CountryListVm
import countryinfo.app.vm.DsgSearchVm


@Composable
fun HomeScreen() {

    val navHostController = rememberNavController()

    val viewModel: CountryListVm = hiltViewModel()

    val searchVm: DsgSearchVm = hiltViewModel()

    val isFav by rememberSaveable { mutableStateOf(viewModel.isFav) }

    val clickedTab by rememberSaveable { mutableStateOf(viewModel.selectedTab) }

    val title = rememberSaveable {
        viewModel.title
    }

    val getSaveScreen = viewModel.getSavedScreen().collectAsState()

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    val scaffoldState = rememberScaffoldState()

    val errorState = viewModel.observeErrorState().collectAsState()

    val dsgErrorState = searchVm.observeErrorState().collectAsState()

    val noInterNetMessage = stringResource(id = R.string.there_is_no_internet)

    LaunchedEffect(key1 = errorState.value, key2 = isConnected, key3 = dsgErrorState.value) {

        if (!isConnected) {
            scaffoldState.snackbarHostState.showSnackbar(noInterNetMessage)
        } else if (errorState.value.isNotEmpty()) {
            scaffoldState.snackbarHostState.showSnackbar(errorState.value)
        }
        else if (dsgErrorState.value.isNotEmpty()) {
            scaffoldState.snackbarHostState.showSnackbar(dsgErrorState.value)
        }
    }

    Scaffold(
        topBar = {
            TopBarConditional(
                title = title.value,
                screenOptions = getSaveScreen.value,
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
            BottomBarConditional(
                navController = navHostController,
                screenOptions = getSaveScreen.value
            )
        },
        scaffoldState = scaffoldState,

        ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = padding.calculateBottomPadding()
                )
        ) {

            Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {

                getSaveScreen.value.let { screenName ->

                    val route = if (screenName == ScreenOptions.SearchScreen) {

                        if (clickedTab.value == titleSearch)
                            BottomTab.TabSearch.route
                        else
                            BottomTab.TabSaved.route
                    } else
                        BottomTab.TabOverview.route

                    SearchNavigationGraph(
                        navController = navHostController, viewModel = viewModel, dsgViewModel = searchVm, route
                    )

                    DefaultSnackBar(
                        snackBarHostState = scaffoldState.snackbarHostState,
                        onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() }
                    )
                }
            }
        }

        BackHandler(enabled = true) {
            viewModel.title.value = titleSearch
            viewModel.setSavedScreen(ScreenOptions.SearchScreen)
            navHostController.navigateUp()

        }
    }
}


@Composable
fun SearchNavigationGraph(
    navController: NavHostController,
    viewModel: CountryListVm,
    dsgViewModel: DsgSearchVm,
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
            viewModel.selectedTab.value = titleSearch
        }
        composable(BottomTab.TabSaved.route) {
            HomeSavedTab(
                navController = navController,
                viewModel = viewModel
            )
            viewModel.selectedTab.value = titleSaved

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

        composable(route = BottomTab.TabDetails.route) {
            CountryDetailsScreen(viewModel)
        }


        composable(route = BottomTab.TabDetails.route) {
            CountryDetailsScreen(viewModel)
        }

        composable(route = BottomTab.TabDsgSearch.route) {
            DsgSearchTab(dsgViewModel)
        }
    }
}