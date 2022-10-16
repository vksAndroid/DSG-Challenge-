package countryinfo.app.utils

import android.Manifest
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun checkLocationPermission(): Boolean {

    val permissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    return when {
        permissionState.hasPermission -> true

        permissionState.shouldShowRationale -> false
        !permissionState.hasPermission && !permissionState.shouldShowRationale -> false
        else -> false
    }
}