package countryinfo.app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import countryinfo.app.ui.screens.detail.CountryMapScreen
import countryinfo.app.ui.screens.detail.DetailOverViewTab
import countryinfo.app.uicomponents.BottomMenu
import countryinfo.app.uicomponents.TopBarDetailScreen
import countryinfo.app.utils.tabs.BottomTab
import countryinfo.app.vm.CountryListVm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CountryDetailsScreen(
    cca3: String,
    viewModel: CountryListVm,
    mFusedLocationClient: FusedLocationProviderClient,
    onBackPress: () -> Unit
) {

    val scrollState = rememberScrollState()
    val navHostController = rememberNavController()

    val countryList = viewModel.observeCountryList().collectAsState().value
    val countryData =
        countryList.first { countryDetailItem -> cca3 == countryDetailItem.cca3 }
    countryData.cca3?.let { viewModel.isCountryFav(it) }

    var isFav by rememberSaveable { mutableStateOf(viewModel._isFav) }

    Scaffold(topBar = {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TopBarDetailScreen(
                isShowNavigation = true,
                isShowSaveIcon = true,
                title = countryData.name?.common ?: "",
                isSaved = isFav.value,
                onFavClick = {
                    if (isFav.value) {
                        viewModel.removeFavourite(countryData)
                    } else {
                        viewModel.addFavourite(countryData)
                    }
                }
            ) {
                onBackPress.invoke()
            }
        }
    },
        bottomBar =
        { BottomMenu(navController = navHostController) }) {
        DetailNavigationGraph(
            navController = navHostController, scrollState = scrollState,
            countryId = cca3, viewModel = viewModel,
            mFusedLocationClient
        )
    }
}


@Composable
fun DetailNavigationGraph(
    navController: NavHostController,
    scrollState: ScrollState,
    countryId: String,
    viewModel: CountryListVm,
    mFusedLocationClient: FusedLocationProviderClient
) {

    NavHost(navController = navController, startDestination = BottomTab.TabOverview.route) {

        composable(BottomTab.TabOverview.route) {
            DetailOverViewTab(cca3 = countryId, viewModel = viewModel)
        }
        composable(BottomTab.TabMap.route) {
            CountryMapScreen(
                navController = navController,
                cca3 = countryId,
                viewModel = viewModel,
                mFusedLocationClient = mFusedLocationClient
            )

        }


    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //CountryDetailsScreen("HKG", null)
}

