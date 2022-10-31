package countryinfo.app.uicomponents.map

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import countryinfo.app.R
import countryinfo.app.uicomponents.scaffold_comp.getDP

/**
 * @param location Latlng of postion to show marker
 * @param locationType Type of Location like Country,Capitala dn region
 */
@Composable
fun MapViewComponent(location: LatLng, showMarker: Boolean, zoomLevel: Float) {

    val cameraPositionState =  rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, zoomLevel)
    }

    GoogleMap(
        modifier = Modifier
            .testTag("map_view")
            .fillMaxWidth()
            .height(getDP(dimenKey = R.dimen.dp_400))
            .padding(start = getDP(dimenKey = R.dimen.dp_12), end = getDP(dimenKey = R.dimen.dp_12)),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(mapToolbarEnabled = false),
        onMapLoaded = {
            cameraPositionState.move(CameraUpdateFactory.newLatLng(location))
        }
    ) {
        if (showMarker) {
            Marker(
                state = MarkerState(location),
                title = stringResource(id = R.string.your_current_location),
                snippet = stringResource(id = R.string.your_current_location)
            )
        }
    }
}