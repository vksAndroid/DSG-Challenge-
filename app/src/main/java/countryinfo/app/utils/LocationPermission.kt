package countryinfo.app.utils

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun isLocationPermissionGranted(): Boolean {

    var isPermissionGranted = true
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionState.launchPermissionRequest()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    when {
        permissionState.hasPermission -> {
//            Text(text = "Location permission has been granted")
            isPermissionGranted = true
        }
        permissionState.shouldShowRationale -> {
//            Column {
//                Text(text = "Location permission is needed")
//            }
            isPermissionGranted = false
        }
        !permissionState.hasPermission && !permissionState.shouldShowRationale -> {
//            Text(text = "Navigate to settings and enable the Location permission")
        }
    }
    return isPermissionGranted
}