package countryinfo.app.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import countryinfo.app.R
import countryinfo.app.api.model.CountryData
import countryinfo.app.repo.CountryListRepo
import countryinfo.app.uicomponents.CountryItemView
import countryinfo.app.uicomponents.TopBarDetailScreen
import countryinfo.app.utils.networkconnection.ConnectionState
import countryinfo.app.utils.networkconnection.connectivityState
import countryinfo.app.vm.CountryListVm
import kotlinx.coroutines.Dispatchers

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun CountrySearchScreen(navController: NavController?, viewModel: CountryListVm?) {

    val progressBar = remember { mutableStateOf(false) }
    val countryList = viewModel?.observeCountryList()?.collectAsState()
    val searchList = viewModel?.observeSearchCountryList()?.collectAsState()

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    Log.d("Search List", Gson().toJson(searchList))

    if (searchList?.value?.isEmpty()!! && countryList?.value?.isEmpty()!!) {
        viewModel.getCountryList()
    }

    Scaffold(topBar = {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TopBarDetailScreen( title = "Countries"){}
        }
    },
        snackbarHost = {
            if (isConnected.not()) {
                Snackbar(
                    action = {
                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = stringResource(R.string.there_is_no_internet))
                }
            }
        }) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchTextField(viewModel!!)
                viewModel.data.value = "sjdhfbwks"
                if (searchList?.value?.isEmpty()!!) {
                    CountryListView(navController, countryList?.value!!)
                } else {
                    CountryListView(navController, searchList.value)
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition", "FlowOperatorInvokedInComposition")
@Composable
fun SearchTextField(viewModel: CountryListVm) {
    val query = viewModel.searchQuery().collectAsState().value

    LaunchedEffect(key1 = query){
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
        textStyle = TextStyle(fontSize = 16.sp),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(20.dp)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryListView(navController: NavController?, countryList: List<CountryData>) {
    val countryDetailScreenNavId = stringResource(id = R.string.country_details)
    LazyColumn {
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
                onItemClicked = { navController?.navigate("$countryDetailScreenNavId/${countryData.cca3}") }
            )
        }
    }
}


@Composable
fun CountryItemTextView(name: String, fontWeight: FontWeight, color: Color) {
    Text(
        text = name,
        fontWeight = fontWeight,
        style = MaterialTheme.typography.body1,
        color = color
    )
}


@Preview(showBackground = true)
@Composable
fun ShowCountrySearchScreenPreview() {
    CountrySearchScreen(null, null)
}