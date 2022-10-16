package countryinfo.app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import countryinfo.app.ui.screens.detail.DetailOverViewTab
import countryinfo.app.vm.CountryListVm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CountryDetailsScreen(viewModel: CountryListVm) {

    DetailOverViewTab(viewModel = viewModel)
}


