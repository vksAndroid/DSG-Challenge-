package countryinfo.app.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SettingsVoice
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import countryinfo.app.R
import countryinfo.app.presentation.vm.CountryListVm
import countryinfo.app.theme.SearchBG
import countryinfo.app.uicomponents.CountryListView
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

    viewModel.title.value = titleSearch

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding()
        ) {

            SearchTextField(viewModel)

            if (searchList.value.isEmpty() && query.length < 2) {
                CountryListView(true, isConnected, errorState, navController, countryList.value) {
                    viewModel.setSavedScreen(ScreenOptions.DetailScreen)
                    viewModel.updateCountryData(it)
                    viewModel.isCountryFav(it.cca3)
                }
            } else {
                CountryListView(true, isConnected, errorState, navController, searchList.value) {
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
    val isVoicePermissionGranted = checkRecordAudioPermission()
    LaunchedEffect(key1 = query) {
        viewModel.searchByDebounce(query)
        if (query.isEmpty()) {
            viewModel.clearSearch()
        }
    }

    TextField(
        value = query,
        onValueChange = {
            viewModel.updateSearchQuery(it)
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                fontWeight = FontWeight.Normal, color = Color.Black
            )
        },
        modifier = Modifier
            .testTag("country_search_text_field")
            .padding(all = getDP(dimenKey = R.dimen.dp_8))
            .fillMaxWidth()
            .background(SearchBG)
            .height(50.dp),
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = EMPTY_STRING) },
        trailingIcon = {
            if (isVoicePermissionGranted) {
                IconButton(
                    modifier = Modifier.padding(
                        end =
                        10.dp
                    ),
                    onClick = {
                        if (isVoicePermissionGranted) {
                            viewModel.convertSpeechToText()
                        }
                    },
                ) {
                    Icon(
                        Icons.Default.SettingsVoice,
                        contentDescription = EMPTY_STRING,
                        tint = Color.Gray
                    )
                }
            } else {
                null
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Gray
        ),
        shape = RoundedCornerShape(getDP(dimenKey = R.dimen.dp_20))
    )
}

@Preview(showBackground = true)
@Composable
fun ShowCountrySearchScreenPreview() {
    //HomeSearchTab(null, null)
}