package countryinfo.app.ui.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import countryinfo.app.R
import countryinfo.app.uicomponents.CountryBasicDetail
import countryinfo.app.uicomponents.CountryNameCard
import countryinfo.app.uicomponents.ImageFullFlag
import countryinfo.app.uicomponents.MapViewComponent
import countryinfo.app.utils.LOCATION_TYPE_CAPITAL
import countryinfo.app.utils.LOCATION_TYPE_COUNTRY
import countryinfo.app.utils.LOCATION_TYPE_CURRENT
import countryinfo.app.utils.isLocationPermissionGranted
import countryinfo.app.vm.CountryListVm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun CountryMapScreen(
    navController: NavController?,
    cca3: String,
    viewModel: CountryListVm?,
    mFusedLocationClient: FusedLocationProviderClient?
) {

    //Local response
//    val countryList = viewModel?.getLocalCountryDetailsList(LocalContext.current)
//    val countryDetails = countryList?.first { countryDetailItem -> cca3 == countryDetailItem.cca3 }
    //Server response
    val countryList = viewModel?.observeCountryList()?.collectAsState()
    val countryDetails =
        countryList?.value?.first { countryDetailItem -> cca3 == countryDetailItem.cca3 }

    ConstraintLayout(
        setComponentsUsingConstraints(), modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        ImageFullFlag(flagImageUrl = countryDetails?.flags?.png!!)

        CountryNameCard(
            title = countryDetails.name?.common!!,
            value = countryDetails.name?.official!!
        )

        CountryBasicDetail(countryDetails)

        Column(
            modifier = Modifier
                .fillMaxSize().padding(top = 8.dp)
                .layoutId("mapColumn")
        ) {
//            countryDetails?.flags?.png?.let { it1 -> ImageFullFlag(flagImageUrl = it1) }
//            CountryNameCard(
//                title = countryDetails?.name?.common!!,
//                value = countryDetails.name?.official!!
//            )
//            CountryBasicDetail(countryDetails)
            if (isLocationPermissionGranted()) {
                viewModel.getCurrentLatLong(mFusedLocationClient!!)
                val currentLocation = viewModel.observeCurrentLocation().collectAsState().value
                val currentLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                MapTextLabel(
                    textLabel = stringResource(id = R.string.your_current_location),
                    textValue = ""
                )
                MapViewComponent(currentLatLng, LOCATION_TYPE_CURRENT)
            }
            MapTextLabel(
                textLabel = "${stringResource(id = R.string.country)} - ",
                textValue = countryDetails?.name?.common!!
            )
            val countryLocation =
                LatLng(countryDetails?.latlng?.get(0) ?: 0.0, countryDetails?.latlng?.get(1) ?: 0.0)
            MapViewComponent(countryLocation, LOCATION_TYPE_COUNTRY)
            MapTextLabel(
                textLabel = "${stringResource(id = R.string.capital)} - ",
                textValue = if (countryDetails.capital.isNotEmpty()) {
                    countryDetails.capital[0]
                } else {
                    ""
                }
            )
            val capitalLocation = LatLng(
                countryDetails?.capitalInfo?.latlng?.get(0) ?: 0.0,
                countryDetails?.capitalInfo?.latlng?.get(1) ?: 0.0
            )
            MapViewComponent(capitalLocation, LOCATION_TYPE_CAPITAL)
        }
    }
}

@Composable
fun MapTextLabel(textLabel: String, textValue: String) {
    Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
        Text(
            text = textLabel,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )
        Text(
            text = textValue,
            style = MaterialTheme.typography.body1,
            color = Color.DarkGray
        )
    }
}

fun setComponentsUsingConstraints(): ConstraintSet {

    return ConstraintSet {

        val idTopFlag = createRefFor("top_flag")
        val idBasicDetail = createRefFor("BasicDetail")
        val idCountry = createRefFor("Country")
        val idMapColumn = createRefFor("mapColumn")

        constrain(idTopFlag) {
            top.linkTo(parent.top, margin = 0.dp)
        }
        constrain(idBasicDetail) {
            top.linkTo(idCountry.bottom, margin = 10.dp)
        }
        constrain(idCountry) {
            bottom.linkTo(idTopFlag.bottom)
            top.linkTo(idTopFlag.bottom)
            start.linkTo(parent.start, margin = 12.dp)
        }
        constrain(idMapColumn) {
            top.linkTo(idBasicDetail.bottom, margin = 10.dp)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MapDefaultPreview() {
    val viewModel: CountryListVm = viewModel()
    CountryMapScreen(null, "HKG", viewModel, null)
}