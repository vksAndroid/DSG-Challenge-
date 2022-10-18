package countryinfo.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import countryinfo.app.data.local.dataconverters.Converters
import countryinfo.app.data.model.CountryData

@Database(entities = [CountryData::class], version = 1)
@TypeConverters(value = [Converters::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun countriesDao(): CountriesDao
}