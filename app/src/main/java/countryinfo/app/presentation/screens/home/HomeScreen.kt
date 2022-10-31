package countryinfo.app.presentation.screens.home

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
import androidx.navigation.compose.rememberNavController
import countryinfo.app.R
import countryinfo.app.presentation.graph.BottomTab
import countryinfo.app.presentation.graph.DsgNavigationGraph
import countryinfo.app.presentation.vm.CountryListVm
import countryinfo.app.presentation.vm.DsgShopVm
import countryinfo.app.uicomponents.scaffold_comp.DsgBottomBar
import countryinfo.app.uicomponents.scaffold_comp.DsgSnackBar
import countryinfo.app.uicomponents.scaffold_comp.DsgTopBar
import countryinfo.app.utils.ScreenOptions
import countryinfo.app.utils.checkLocationPermission
import countryinfo.app.utils.networkconnection.ConnectionState
import countryinfo.app.utils.networkconnection.connectivityState
import countryinfo.app.utils.titleSearch


@Composable
fun HomeScreen() {

    val activity = (LocalContext.current as? Activity)

    val navHostController = rememberNavController()

    val viewModel: CountryListVm = hiltViewModel()

    val searchVm: DsgShopVm = hiltViewModel()

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

    val isAmerica = searchVm.observeIsAmerica().collectAsState().value
    if (checkLocationPermission()) {
        viewModel.getCurrentLatLong()
        val currentLocation =
            viewModel.observeCurrentLocation().collectAsState().value
        searchVm.getCountryByLocation(currentLocation)
    }

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
            DsgTopBar(
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
            DsgBottomBar(
                navController = navHostController,
                screenOptions = getSaveScreen.value,
                isCurrentLocationAmerica = isAmerica
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

                    DsgNavigationGraph(
                        navController = navHostController, viewModel = viewModel,searchVm, route
                    )

                    DsgSnackBar(
                        snackBarHostState = scaffoldState.snackbarHostState,
                        onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() }
                    )
                }
            }
        }

        BackHandler(enabled = true) {
            viewModel.title.value = titleSearch
            viewModel.setSavedScreen(ScreenOptions.SearchScreen)

            // Handle Back Stack here for tab
            if(navHostController.currentDestination?.route
                == BottomTab.TabSearch.route
                ||navHostController.currentDestination?.route
                == BottomTab.TabSaved.route
                ||navHostController.currentDestination?.route
                == BottomTab.TabDsgSearch.route){
                activity?.finish()
            } else{
                navHostController.navigateUp()
            }

        }
    }
}