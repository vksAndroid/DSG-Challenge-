package countryinfo.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import countryinfo.app.data.model.CountryData

@Dao
interface CountriesDao {

    @Insert
    suspend fun addToFavourite(countryData: CountryData) :Long

    @Query("Delete from countries where  cca3 = :name")
    suspend fun removeFromFavourite(name: String) :Int

    @Query("Select * from countries")
    suspend fun getAllFavorite(): List<CountryData>

    @Query("Select * from countries where cca3 = :name")
    suspend fun isFav(name: String): CountryData?
}