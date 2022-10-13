package countryinfo.app.ui.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import countryinfo.app.utils.networkconnection.ConnectionState
import countryinfo.app.utils.networkconnection.connectivityState
import countryinfo.app.vm.CountryListVm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun HomeSavedTab(navController: NavController?, viewModel: CountryListVm) {


    val countrySavedList = viewModel.observeSavedCountryList()?.collectAsState()
    viewModel.title.value = "Saved"

        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Column(modifier = Modifier.fillMaxSize()) {
                CountryListView(navController, countrySavedList?.value!!){

                }
                Text(text = "SAVED SCREEN", modifier = Modifier.padding(30.dp))

            }
        }
}




@Preview(showBackground = true)
@Composable
fun ShowSavedTabPreview() {
    //HomeSavedTab(null, null)
}