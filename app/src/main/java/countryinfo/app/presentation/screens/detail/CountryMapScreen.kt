package countryinfo.app.presentation.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import countryinfo.app.R
import countryinfo.app.data.model.CountryData
import countryinfo.app.presentation.vm.CountryListVm
import countryinfo.app.uicomponents.CountryBasicDetail
import countryinfo.app.uicomponents.CountryNameCard
import countryinfo.app.uicomponents.ImageFullFlag
import countryinfo.app.uicomponents.map.MapLabel
import countryinfo.app.uicomponents.map.MapViewComponent
import countryinfo.app.utils.*

@Composable
fun CountryMapScreen(
    viewModel: CountryListVm
) {

    val countryDetail = viewModel.observeCountryData().collectAsState().value

    ConstraintLayout(
        setComponentsUsingConstraints1(), modifier = Modifier.testTag("country_map_screen")
            .fillMaxSize()
    ) {
        LoadContent(
            countryDetail = countryDetail,
            viewModel = viewModel,
            locationEnabled = checkLocationPermission()
        )
    }
}

@Composable
fun LoadContent(
    countryDetail: CountryData,
    viewModel: CountryListVm,
    locationEnabled: Boolean
) {
    val list = if (locationEnabled) {
        listOf(MapType.Header, MapType.CurrentLocation, MapType.Country, MapType.Capital)
    } else {
        listOf(MapType.Header, MapType.Country, MapType.Capital)
    }

    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth().padding(bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(list) { item ->

            when (item) {
                is MapType.Header -> {
                    ConstraintLayout(constraintSet = mapHeaderConstraints(),
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                        countryDetail.flags.png.let {
                            ImageFullFlag(flagImageUrl = it)
                        }
                        val official = countryDetail.name.official
                        val name = countryDetail.name.common

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
                        MapLabel(
                            textLabel = stringResource(id = R.string.your_current_location),
                            textValue = EMPTY_STRING
                        )
                        MapViewComponent(currentLatLng, true, MapZoomLevel(
                            location = currentLatLng,
                            zoom =  15f
                        ))
                    }
                }

                is MapType.Country -> {
                    MapLabel(
                        textLabel = "${stringResource(id = R.string.country)} - ",
                        textValue = countryDetail.name.common
                    )
                    val countryLocation: LatLng = try {

                        if (countryDetail.latlng.isEmpty()) {
                            LatLng(0.0, 0.0)
                        } else {
                            LatLng(
                                countryDetail.latlng[0], countryDetail.latlng[1]
                            )
                        }
                    } catch (ex: Exception) {
                        LatLng(0.0, 0.0)
                    }
                        MapViewComponent(countryLocation, false, MapZoomLevel(
                            location = countryLocation,
                            zoom =  6f
                        ))

                }
                is MapType.Capital -> {
                    MapLabel(
                        textLabel = "${stringResource(id = R.string.capital)} - ",
                        textValue = if (countryDetail.capital.isEmpty()) {
                            EMPTY_STRING
                        } else {
                            countryDetail.capital[0]
                        }
                    )

                    val capitalLocation: LatLng = try {

                        if (countryDetail.capitalInfo?.latlng.isNullOrEmpty()) {
                            LatLng(0.0, 0.0)
                        } else {
                            LatLng(
                                countryDetail.capitalInfo?.latlng?.get(0) ?: 0.0, countryDetail.capitalInfo?.latlng?.get(1)
                                    ?: 0.0
                            )
                        }
                    } catch (ex: Exception) {
                        LatLng(0.0, 0.0)
                    }

                    MapViewComponent(capitalLocation, false, MapZoomLevel(
                        location = capitalLocation,
                        zoom = 11f
                    ))
                }
            }
        }
    }


}

fun setComponentsUsingConstraints1(): ConstraintSet {

    return ConstraintSet {

        val idTopFlag = createRefFor(idTopFlag)
        val idBasicDetail = createRefFor(idBasicDetail)
        val idCountry = createRefFor(idCountry)
        val idMapColumn = createRefFor(idMapColumn)

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

        val idTopFlag = createRefFor(idTopFlag)
        val idBasicDetail = createRefFor(idBasicDetail)
        val idCountry = createRefFor(idCountry)

        constrain(idTopFlag) {
            top.linkTo(parent.top)
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

@Composable
fun MapZoomLevel(location: LatLng, zoom: Float): CameraPositionState {
    return rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, zoom)
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