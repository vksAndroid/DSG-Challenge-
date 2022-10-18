package countryinfo.app.ui.screens.search

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import countryinfo.app.R
import countryinfo.app.uicomponents.CountryListView
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.EMPTY_STRING
import countryinfo.app.utils.ScreenOptions
import countryinfo.app.utils.networkconnection.ConnectionState
import countryinfo.app.utils.networkconnection.connectivityState
import countryinfo.app.utils.titleSearch
import countryinfo.app.vm.CountryListVm

@Composable
fun HomeSearchTab(navController: NavController?, viewModel: CountryListVm) {

    val countryList = viewModel.observeCountryList().collectAsState()
    val searchList = viewModel.observeSearchCountryList().collectAsState()
    val errorState = viewModel.observeErrorState().collectAsState()

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

     LaunchedEffect(key1 = countryList){
        viewModel.getCountryList()
    }

    viewModel.title.value = titleSearch

        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding()) {

                SearchTextField(viewModel)

                if (searchList.value.isEmpty()) {
                    CountryListView(true,isConnected,errorState, navController, countryList.value) {
                        viewModel.setSavedScreen(ScreenOptions.DetailScreen)
                        viewModel.updateCountryData(it)
                        viewModel.isCountryFav(it.cca3)
                    }
                } else {
                    CountryListView(true, isConnected,errorState,navController, searchList.value) {
                        viewModel.setSavedScreen(ScreenOptions.DetailScreen)
                        viewModel.updateCountryData(it)
                        viewModel.isCountryFav(it.cca3)

                    }
                }
            }
        }


}

@Composable
fun SearchTextField(viewModel: CountryListVm) {

    val query = viewModel.searchQuery().collectAsState().value

    LaunchedEffect(key1 = query) {
        viewModel.searchByDebounce(query)
        if(query.isEmpty()){
            viewModel.clearSearch()
        }
    }

    TextField(
        value = query,
        onValueChange = {
            viewModel.updateSearchQuery(it)
        },
        placeholder = { Text(text = stringResource(id = R.string.search)) },
        modifier = Modifier.testTag("country_search_text_field")
            .padding(all = getDP(dimenKey = R.dimen.dp_8))
            .fillMaxWidth()
             .border(width =getDP(dimenKey = R.dimen.dp_8), color = Color.White, shape = RoundedCornerShape(getDP(dimenKey = R.dimen.dp_20))),
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = EMPTY_STRING) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Gray
        ),
        shape = RoundedCornerShape(getDP(dimenKey = R.dimen.dp_20))
    )
}

