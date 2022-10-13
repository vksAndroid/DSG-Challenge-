package countryinfo.app.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import countryinfo.app.api.model.CountryData
import countryinfo.app.local.dataconverters.Converters

@Database(entities = [CountryData::class], version = 1)
@TypeConverters(value = [Converters::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun countriesDao(): CountriesDao
}