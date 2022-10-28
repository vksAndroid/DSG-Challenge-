package countryinfo.app.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SettingsVoice
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import countryinfo.app.presentation.vm.CountryListVm
import countryinfo.app.uicomponents.CountryListView
import countryinfo.app.uicomponents.DsgSearchComponent
import countryinfo.app.utils.*
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.EMPTY_STRING
import countryinfo.app.utils.ScreenOptions
import countryinfo.app.utils.checkRecordAudioPermission
import countryinfo.app.utils.networkconnection.ConnectionState
import countryinfo.app.utils.networkconnection.connectivityState
import countryinfo.app.utils.titleSearch

@Composable
fun HomeSearchTab(navController: NavController?, viewModel: CountryListVm) {

    val countryList = viewModel.observeCountryList().collectAsState()
    val searchList = viewModel.observeSearchCountryList().collectAsState()
    val errorState = viewModel.observeErrorState().collectAsState()

    val query = viewModel.searchQuery().collectAsState().value

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    LaunchedEffect(key1 = countryList) {
        if (countryList.value.isEmpty()) {
            viewModel.getCountryList()
        }
    }

    LaunchedEffect(key1 = query) {

    if(query!=viewModel.lastSearchItem.value) {

         viewModel.searchByDebounce(query)
    }
        if (query.isEmpty()) {
            viewModel.clearSearch()
        }
    }

    viewModel.title.value = titleSearch

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding()
        ) {

            DsgSearchComponent(query =query, onValueChange = {
                viewModel.updateSearchQuery(it)
            } ) {
                viewModel.convertSpeechToText()
            }

            if (searchList.value.isEmpty() && query.length < 2) {
                CountryListView(true, isConnected, errorState, navController, countryList.value) {

                    viewModel.updateScreenData(ScreenOptions.DetailScreen,it)

                }
            } else {
                CountryListView(true, isConnected, errorState, navController, searchList.value) {
                    viewModel.updateScreenData(ScreenOptions.DetailScreen,it)
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ShowCountrySearchScreenPreview() {
    //HomeSearchTab(null, null)
}