package countryinfo.app.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import countryinfo.app.api.model.CountryData

@Dao
interface CountriesDao {

    @Insert
    suspend fun insertFavourite(countryData: CountryData)


    @Query("Delete from countries where  cca3 = :name")
    suspend fun deleteFavourite(name: String)

    @Query("Select * from countries")
    suspend fun getAllFavorite(): List<CountryData>

    @Query("Select * from countries where cca3 = :name")
    suspend fun isFav(name: String) : CountryData
}