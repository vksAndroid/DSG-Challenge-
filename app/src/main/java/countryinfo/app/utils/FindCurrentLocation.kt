package countryinfo.app.utils

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
@SuppressLint("MissingPermission")
fun GetCurrentLocation(currentLocationStateFlow: MutableStateFlow<Location>) {
    LocationServices.getFusedLocationProviderClient(
        LocalContext.current
    ).getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                CancellationTokenSource().token

            override fun isCancellationRequested() = false
        }).addOnSuccessListener { location ->
        if (location != null) {
            currentLocationStateFlow.value = location
        }
    }
}
