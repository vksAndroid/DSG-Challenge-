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
    val cameraPositionState = rememberCameraPositionState {
        position = when (locationType) {
            MapType.CurrentLocation -> CameraPosition.fromLatLngZoom(location, 15f)
            MapType.Country -> CameraPosition.fromLatLngZoom(location, 1f)
            else -> CameraPosition.fromLatLngZoom(location, 10f)
        }
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        cameraPositionState = cameraPositionState
    ) {
        if (locationType == MapType.CurrentLocation) {
            cameraPositionState.move(CameraUpdateFactory.newLatLng(location))
            Marker(
                state = MarkerState(location),
                title = stringResource(id = R.string.your_current_location),
                snippet = stringResource(id = R.string.your_current_location)
            )
        }
    }
}