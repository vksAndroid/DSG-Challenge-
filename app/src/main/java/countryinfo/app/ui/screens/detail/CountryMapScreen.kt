package countryinfo.app.ui.screens.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.constraintlayout.compose.Dimension
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import countryinfo.app.R
import countryinfo.app.api.model.CountryData
import countryinfo.app.uicomponents.CountryBasicDetail
import countryinfo.app.uicomponents.CountryNameCard
import countryinfo.app.uicomponents.ImageFullFlag
import countryinfo.app.uicomponents.MapViewComponent
import countryinfo.app.utils.*
import countryinfo.app.utils.isLocationPermissionGranted
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
    ) {
        loadContent(
            countryDetail = countryDetail,
            viewModel = viewModel,
            locationEnabled = isLocationPermissionGranted()
        )
    }
}

@Composable
fun loadContent(
    countryDetail: CountryData,
    viewModel: CountryListVm,
    locationEnabled: Boolean
) {
    val list = if (locationEnabled) {
        listOf(MapType.Header, MapType.CurrentLocation, MapType.Country, MapType.Capital)
    } else {
        listOf(MapType.Header, MapType.Country, MapType.Capital)
    }
    Log.d("Country Data:", Gson().toJson(countryDetail))
    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(list) { item ->

            when (item) {
                is MapType.Header -> {
                    ConstraintLayout(constraintSet = mapHeaderConstraints(),
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                        countryDetail.flags?.png?.let {
                            ImageFullFlag(flagImageUrl = it)
                        }
                        val official = countryDetail.name?.official ?: ""
                        val name = countryDetail.name?.common ?: ""

                        CountryNameCard(title = name, value = official)
                        CountryBasicDetail(countryDetail)
                    }
                }
                is MapType.CurrentLocation -> {
                    if (locationEnabled) {
                        viewModel.getCurrentLatLong()
                        val currentLocation =
                            viewModel.observeCurrentLocation().collectAsState().value
                        val currentLatLng =
                            LatLng(currentLocation.latitude, currentLocation.longitude)
                        MapTextLabel(
                            textLabel = stringResource(id = R.string.your_current_location),
                            textValue = ""
                        )
                        MapViewComponent(currentLatLng, MapType.CurrentLocation)
                    }
                }

                is MapType.Country -> {
                    MapTextLabel(
                        textLabel = "${stringResource(id = R.string.country)} - ",
                        textValue = countryDetail.name?.common ?: ""
                    )
                    var countryLocation: LatLng
                    try {

                        if (countryDetail.latlng.isNullOrEmpty()) {
                            countryLocation = LatLng(0.0, 0.0)
                        } else {
                            countryLocation = LatLng(
                                countryDetail?.latlng?.get(0) ?: 0.0, countryDetail?.latlng?.get(1)
                                    ?: 0.0
                            )
                        }
                    } catch (ex: Exception) {
                        countryLocation = LatLng(0.0, 0.0)
                    }
                        MapViewComponent(countryLocation, MapType.Capital)

                }
                is MapType.Capital -> {
                    MapTextLabel(
                        textLabel = "${stringResource(id = R.string.capital)} - ",
                        textValue = if (countryDetail.capital.isNullOrEmpty()) {
                            EMPTY_STRING
                        } else {
                            countryDetail.capital[0]
                        }
                    )

                    var capitalLocation: LatLng
                    try {

                        if (countryDetail.capitalInfo?.latlng.isNullOrEmpty()) {
                            capitalLocation = LatLng(0.0, 0.0)
                        } else {
                            capitalLocation = LatLng(
                                countryDetail.capitalInfo?.latlng?.get(0) ?: 0.0, countryDetail.capitalInfo?.latlng?.get(1)
                                    ?: 0.0
                            )
                        }
                    } catch (ex: Exception) {
                        capitalLocation = LatLng(0.0, 0.0)
                    }

                    MapViewComponent(capitalLocation, MapType.Capital)
                }
            }
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
            bottom.linkTo(parent.bottom)
        }
    }

}


fun mapHeaderConstraints(): ConstraintSet {

    return ConstraintSet {

        val idTopFlag = createRefFor("top_flag")
        val idBasicDetail = createRefFor("BasicDetail")
        val idCountry = createRefFor("Country")



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
    }
}

@Preview(showBackground = true)
@Composable
fun MapDefaultPreview() {
    // CountryMapScreen(  viewModel, null)
}


sealed class MapType {
    object Header : MapType()
    object CurrentLocation : MapType()
    object Country : MapType()
    object Capital : MapType()
}