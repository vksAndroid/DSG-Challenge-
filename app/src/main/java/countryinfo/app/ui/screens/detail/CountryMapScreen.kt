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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import countryinfo.app.R
import countryinfo.app.uicomponents.CountryBasicDetail
import countryinfo.app.uicomponents.CountryNameCard
import countryinfo.app.uicomponents.ImageFullFlag
import countryinfo.app.uicomponents.MapViewComponent
import countryinfo.app.utils.*
import countryinfo.app.vm.CountryListVm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun CountryMapScreen(
    viewModel: CountryListVm
) {

    val countryDetail = viewModel.observeCountryData().collectAsState().value

    ConstraintLayout(
        setComponentsUsingConstraints1(), modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        countryDetail.flags?.png?.let {
            ImageFullFlag(flagImageUrl = it)
        }
        val official = countryDetail.name?.official ?: EMPTY_STRING
        val name = countryDetail.name?.common ?: EMPTY_STRING

        CountryNameCard(title = name, value = official)

        CountryBasicDetail(countryDetail)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .layoutId("mapColumn")
        ) {

            if (isLocationPermissionGranted()) {
                viewModel.getCurrentLatLong(
                    LocationServices.getFusedLocationProviderClient(
                    LocalContext.current
                ))
                val currentLocation = viewModel.observeCurrentLocation().collectAsState().value
                val currentLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                MapTextLabel(
                    textLabel = stringResource(id = R.string.your_current_location),
                    textValue = EMPTY_STRING
                )
                MapViewComponent(currentLatLng, LOCATION_TYPE_CURRENT)
            }

            MapTextLabel(
                textLabel = "${stringResource(id = R.string.country)} - ",
                textValue = name
            )

            val countryLocation =
                LatLng(countryDetail.latlng[0] ?: 0.0, countryDetail.latlng[1] ?: 0.0)
            MapViewComponent(countryLocation, LOCATION_TYPE_COUNTRY)
            MapTextLabel(
                textLabel = "${stringResource(id = R.string.capital)} - ",
                textValue = if (countryDetail.capital.isNotEmpty()) {
                    countryDetail.capital[0]
                } else {
                    EMPTY_STRING
                }
            )
            val capitalLocation = LatLng(
                countryDetail.capitalInfo?.latlng?.get(0) ?: 0.0,
                countryDetail.capitalInfo?.latlng?.get(1) ?: 0.0
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

fun setComponentsUsingConstraints1(): ConstraintSet {

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
    // CountryMapScreen(  viewModel, null)
}