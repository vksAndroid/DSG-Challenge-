package countryinfo.app.di.hiltmodules

import android.content.Context
import androidx.room.Room
import countryinfo.app.local.AppDatabase
import countryinfo.app.local.CountriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "countries"
        ).build()
    }

    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): CountriesDao {
        return appDatabase.countriesDao()
    }


}