package countryinfo.app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import countryinfo.app.R
import countryinfo.app.ui.screens.detail.DetailOverViewTab
import countryinfo.app.uicomponents.BottomMenu
import countryinfo.app.uicomponents.CustomAppBar
import countryinfo.app.utils.tabs.DetailBottomTab
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
    val countryDetails =
        countryList?.first { countryDetailItem -> cca3 == countryDetailItem.cca3 }

    Scaffold(topBar = {
        CustomAppBar(
            title = stringResource(id = R.string.countries),
            true
        ) { onBackPress.invoke() }
    },
        bottomBar =
        { BottomMenu(navController = navHostController) }) {
        DetailNavigationGraph(
            navController = navHostController, scrollState = scrollState,
            countryId = cca3, viewModel = viewModel!!,
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

    //"$countryDetailScreenNavId$cid"
    NavHost(navController = navController, startDestination = DetailBottomTab.TabOverview.route) {

        composable(DetailBottomTab.TabOverview.route) {
            DetailOverViewTab(cca3 = countryId, viewModel = viewModel)

        }
        composable(DetailBottomTab.TabMap.route) {
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

