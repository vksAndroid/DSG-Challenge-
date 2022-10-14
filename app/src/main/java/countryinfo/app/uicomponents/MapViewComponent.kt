package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import countryinfo.app.R
import countryinfo.app.ui.screens.detail.MapType

@Composable
fun MapViewComponent(location: LatLng, locationType: MapType) {
    val cameraCurrentLocation = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15f)
    }

    val cameraCountry = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 6f)
    }

    val cameraCapital = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 11f)
    }

    val zoom = when (locationType) {
        is MapType.CurrentLocation -> {
            cameraCurrentLocation
        }
        is MapType.Country -> {
            cameraCountry
        }
        is MapType.Capital -> {
            cameraCapital
        }
        else -> {cameraCapital}
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        cameraPositionState = zoom
    ) {
        zoom.move(CameraUpdateFactory.newLatLng(location))
        if (locationType == MapType.CurrentLocation) {

            Marker(
                state = MarkerState(location),
                title = stringResource(id = R.string.your_current_location),
                snippet = stringResource(id = R.string.your_current_location)
            )
        }
    }
}