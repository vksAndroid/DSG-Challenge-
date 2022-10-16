package countryinfo.app.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import countryinfo.app.api.model.CountryData
import countryinfo.app.api.model.translations.Isl
import countryinfo.app.api.model.translations.Name
import countryinfo.app.api.model.translations.NativeName
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var countriesDao: CountriesDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        countriesDao = db.countriesDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun testAddData() = runBlocking {
        val data = CountryData(
            cca3 = "Ind",
            name = Name("India", "Republic of India", NativeName(Isl("Bharat", "Republic Bharat")))
        )
        countriesDao.addToFavourite(data)
        val dbData = countriesDao.isFav("Ind")
        assertEquals(data.cca3, dbData?.cca3)
    }

    @Test
    fun testRemoveData() = runBlocking {
        val data = CountryData(
            cca3 = "Ind",
            name = Name("India", "Republic of India", NativeName(Isl("Bharat", "Republic Bharat")))
        )
        countriesDao.addToFavourite(data)
        val result = countriesDao.isFav("Ind")
        assertEquals(data.cca3, result?.cca3)
        delay(200)
        countriesDao.removeFromFavourite("Ind")
        val dbData = countriesDao.isFav("Ind")
        assertNull(dbData)
    }

    @Test
    fun testGetAllFavourite() = runBlocking {
        val data = CountryData(
            cca3 = "Ind",
            name = Name("India", "Republic of India", NativeName(Isl("Bharat", "Republic Bharat")))
        )

        val data1 = CountryData(
            cca3 = "Spa",
            name = Name("Spain", "Spain", NativeName(Isl("Spain", "Republic Spain")))
        )
        countriesDao.addToFavourite(data)
        countriesDao.addToFavourite(data1)
        val dbData = countriesDao.getAllFavorite()
        assertEquals(2, dbData.size)
    }

    @Test
    fun testisFavourite() = runBlocking {
        val data = CountryData(
            cca3 = "Ind",
            name = Name("India", "Republic of India", NativeName(Isl("Bharat", "Republic Bharat")))
        )

        val data1 = CountryData(
            cca3 = "Spa",
            name = Name("Spain", "Spain", NativeName(Isl("Spain", "Republic Spain")))
        )
        countriesDao.addToFavourite(data)
        countriesDao.addToFavourite(data1)
        val dbData = countriesDao.isFav("Ind")
        assertNotNull(dbData)
    }

}