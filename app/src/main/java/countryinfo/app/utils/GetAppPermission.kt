package countryinfo.app.utils

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetAppPermissions() {

    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionState.launchMultiplePermissionRequest()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun checkRecordAudioPermission(): Boolean {

    val permissionState = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)

    return when {
        permissionState.hasPermission -> true

        permissionState.shouldShowRationale -> false
        !permissionState.hasPermission && !permissionState.shouldShowRationale -> false
        else -> false
    }
}
