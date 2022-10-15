package countryinfo.app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import countryinfo.app.ui.screens.detail.DetailOverViewTab
import countryinfo.app.vm.CountryListVm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CountryDetailsScreen(viewModel: CountryListVm) {


    DetailOverViewTab(viewModel = viewModel)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //CountryDetailsScreen("HKG", null)
}

