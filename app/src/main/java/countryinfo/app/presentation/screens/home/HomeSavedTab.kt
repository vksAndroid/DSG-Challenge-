package countryinfo.app.presentation.screens.home

 import androidx.compose.foundation.Image
 import androidx.compose.foundation.layout.Column
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.material.Surface
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.LaunchedEffect
 import androidx.compose.runtime.collectAsState
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.graphics.Color
 import androidx.compose.ui.platform.testTag
 import androidx.compose.ui.res.painterResource
 import androidx.navigation.NavController
 import countryinfo.app.R
 import countryinfo.app.presentation.vm.CountryListVm
 import countryinfo.app.uicomponents.CountryListView
 import countryinfo.app.utils.ScreenOptions
 import countryinfo.app.utils.titleSaved

@Composable
fun HomeSavedTab(navController: NavController?, viewModel: CountryListVm) {

    val countrySavedList = viewModel.observeSavedCountryList().collectAsState()

    val errorState = viewModel.observeErrorState().collectAsState()

    LaunchedEffect(key1 = countrySavedList) {
        viewModel.getALlFavourite()
    }

    viewModel.title.value = titleSaved

    Surface(modifier = Modifier
        .fillMaxSize()
        .testTag("home_saved_screen"), color = Color.White) {
        Column(modifier = Modifier.fillMaxSize()) {

            if (countrySavedList.value.isEmpty()) {

                Image(painterResource(R.drawable.no_search_found),
                    "content description")
            } else {
                CountryListView(
                    showShimmer = false,
                    true,
                    errorState,
                    navController,
                    countrySavedList.value
                ) {
                    viewModel.updateScreenData(ScreenOptions.DetailScreen,it)
                }
            }
        }
    }
}