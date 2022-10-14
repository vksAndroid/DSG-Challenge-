package countryinfo.app.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import countryinfo.app.Graph
import countryinfo.app.api.model.CountryData
import countryinfo.app.uicomponents.CountryItemView
import countryinfo.app.uicomponents.LoadingShimmerEffect
import countryinfo.app.utils.ScreenOptions
import countryinfo.app.utils.networkconnection.ConnectionState
import countryinfo.app.utils.networkconnection.connectivityState
import countryinfo.app.vm.CountryListVm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun HomeSearchTab(navController: NavController?, viewModel: CountryListVm) {

    val countryList = viewModel.observeCountryList().collectAsState()
    val searchList = viewModel.observeSearchCountryList().collectAsState()

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    /*if (searchList.value.isEmpty() && countryList.value.isEmpty()) {
        viewModel.getCountryList()
    }*/

    LaunchedEffect(key1 = countryList){
        viewModel.getCountryList()
    }

    viewModel.title.value = "Search"

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding()) {

            SearchTextField(viewModel)

            if (searchList.value.isEmpty()) {
                countryList.value?.let {
                    CountryListView(navController, it) {
                        viewModel.setSavedScreen(ScreenOptions.DetailScreen)
                        viewModel.updateCountryData(it)
                        viewModel.isCountryFav(it.cca3)
                    }
                }
            } else {
                CountryListView(navController, searchList.value) {
                    viewModel.setSavedScreen(ScreenOptions.DetailScreen)
                    viewModel.updateCountryData(it)
                    viewModel.isCountryFav(it.cca3)

                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition", "FlowOperatorInvokedInComposition")
@Composable
fun SearchTextField(viewModel: CountryListVm) {
    val query = viewModel.searchQuery().collectAsState().value

    LaunchedEffect(key1 = query) {
        viewModel.scheduleSearch(query)
    }

    TextField(
        value = query,
        onValueChange = {
            if (it.isEmpty()) {
                viewModel.clearSearch()
            }
            viewModel.updateSearchQuery(it)
        },
        placeholder = { Text(text = "Search") },
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .border(width = 8.dp, color = Color.White, shape = RoundedCornerShape(20.dp)),
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Gray
        ),
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
fun CountryListView(
    navController: NavController?,
    countryList: List<CountryData>,
    changeState: (countryData: CountryData) -> Unit
) {

    if (countryList.isEmpty()) {
        LazyColumn() {
            repeat(6) {
                item { LoadingShimmerEffect() }
            }
        }
    } else {

        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            items(items = countryList) { countryData ->

                CountryItemView(
                    commonName = countryData.name?.common,
                    officialName = countryData.name?.official,
                    capitalName = if (countryData.capital.isNotEmpty()) {
                        countryData.capital[0]
                    } else {
                        ""
                    },
                    countryFlag = countryData.flags?.png,
                    onItemClicked = {
                        changeState.invoke(countryData)

                        navController?.navigate(Graph.CountryDetail)
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowCountrySearchScreenPreview() {
    //HomeSearchTab(null, null)
}