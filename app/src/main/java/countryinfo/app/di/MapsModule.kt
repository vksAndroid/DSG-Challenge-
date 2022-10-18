package countryinfo.app.di

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class MapsModule {

    /**
     * Provides BaseUrl as string
     */
    @Singleton
    @Provides
    fun provideFusedLocationClient(@ApplicationContext appContext: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(
            appContext
        )
    }

    @Singleton
    @Provides
    fun provideGeocoder(@ApplicationContext appContext: Context): Geocoder {
        return Geocoder(appContext, Locale.getDefault())
    }
}