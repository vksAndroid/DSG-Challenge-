package countryinfo.app.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavController
import countryinfo.app.utils.ScreenOptions
import countryinfo.app.vm.CountryListVm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun HomeSavedTab(navController: NavController?, viewModel: CountryListVm) {

    val countrySavedList = viewModel.observeSavedCountryList().collectAsState()

    LaunchedEffect(key1 = countrySavedList) {
        viewModel.getALlFavourite()
    }

    viewModel.title.value = "Saved"

    Surface(modifier = Modifier.fillMaxSize().testTag("home_saved_screen"), color = Color.White) {
        Column(modifier = Modifier.fillMaxSize()) {
            CountryListView(false, navController, countrySavedList.value) {
                viewModel.setSavedScreen(ScreenOptions.DetailScreen)
                viewModel.updateCountryData(it)
                viewModel.isCountryFav(it.cca3)
            }
        }
    }
}