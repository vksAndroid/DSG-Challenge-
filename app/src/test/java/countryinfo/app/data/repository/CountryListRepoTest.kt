package countryinfo.app.data.repository

import countryinfo.app.data.ApiInterface
import countryinfo.app.data.model.CountryData
import countryinfo.app.data.local.CountriesDao
import countryinfo.app.utils.ApiResult
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import retrofit2.Response

class CountryListRepoTest {

    private val apiService = mockk<ApiInterface>()
    private val dao = mockk<CountriesDao>()
    private val repository = CountryListRepo(apiService, dao)


    @Test
    fun `get CountryList emit Success`() = runBlocking {
        val responseMock = mockk<Response<List<CountryData>>>()
        every { responseMock.isSuccessful }.returns(true)

        coEvery { apiService.getCountryList() }.returns(Response.success(listOf(CountryData())))

        repository.getCountryList().collect {
            when (it) {
                is ApiResult.Success -> {
                    assertEquals(1, it.value.size)
                    assertTrue(true)
                }
                is ApiResult.Failure -> {
                    assertTrue(false)
                }
                is ApiResult.Loading -> {
                    assertTrue(true)
                }
                else -> {
                    assertTrue(true)
                }
            }
        }
    }

    @Test
    fun `get CountryList emit failure`() = runBlocking {
        val responseMock = mockk<Response<List<CountryData>>>()
        val responseBodyMock = mockk<ResponseBody>()

        every { responseMock.isSuccessful }.returns(false)

        every { responseMock.message() }.returns("Error occurred")
        every { responseMock.errorBody() }.returns(responseBodyMock)

        coEvery { apiService.getCountryList() }.returns(responseMock)

        repository.getCountryList().collect {
            when (it) {
                is ApiResult.Success -> {
                    assertTrue(false)
                }
                is ApiResult.Failure -> {
                    assertEquals(it.message, "Error occurred")
                    assertTrue(true)
                }
                is ApiResult.Loading -> {
                    assertTrue(true)
                }
                else -> {
                    assertTrue(true)
                }
            }
        }
    }


    @Test
    fun `get Countries by name emit Success`() = runBlocking {
        val responseMock = mockk<Response<List<CountryData>>>()
        every { responseMock.isSuccessful }.returns(true)

        coEvery { apiService.getCountryByName("data") }.returns(Response.success(listOf(CountryData())))

        repository.getCountriesByName("data").collect {
            when (it) {
                is ApiResult.Success -> {
                    assertEquals(1, it.value.size)
                    assertTrue(true)
                }
                is ApiResult.Failure -> {
                    assertTrue(false)
                }
                is ApiResult.Loading -> {
                    assertTrue(true)
                }
                else -> {
                    assertTrue(true)
                }
            }
        }
    }

    @Test
    fun `get Countries by name emit failure`() = runBlocking {
        val responseMock = mockk<Response<List<CountryData>>>()
        val responseBodyMock = mockk<ResponseBody>()

        every { responseMock.isSuccessful }.returns(false)

        every { responseMock.message() }.returns("Error occurred")
        every { responseMock.errorBody() }.returns(responseBodyMock)

        coEvery { apiService.getCountryByName("ABC") }.returns(responseMock)

        repository.getCountriesByName("ABC").collect {
            when (it) {
                is ApiResult.Success -> {
                    assertTrue(false)
                }
                is ApiResult.Failure -> {
                    assertEquals(it.message, "Error occurred")
                    assertTrue(true)
                }
                is ApiResult.Loading -> {
                    assertTrue(true)
                }
                else -> {
                    assertTrue(true)
                }
            }
        }
    }

    @Test
    fun `test add to favourite success`() = runBlocking {
        coEvery { dao.addToFavourite(CountryData()) }.returns(1)

        val result = repository.addToFavourite(CountryData())
        assertEquals(1, result)
    }

    @Test
    fun `test add to favourite failure`() = runBlocking {
        coEvery { dao.addToFavourite(CountryData()) }.returns(0)
        val result = repository.addToFavourite(CountryData())
        assertEquals(0, result)
    }

    @Test
    fun `test remove from favourite failure`() = runBlocking {
        coEvery { dao.removeFromFavourite("Spain") }.returns(0)
        val result = repository.removeFromFavourite("Spain")
        assertEquals(0, result)
    }

    @Test
    fun `test get all favourite success`() = runBlocking {
        coEvery { dao.getAllFavorite() }.returns(listOf(CountryData(), CountryData()))
        val result = repository.getALlFavourite()
        assertEquals(2, result.size)
    }

    @Test
    fun `test get all favourite success return empty list`() = runBlocking {
        coEvery { dao.getAllFavorite() }.returns(emptyList())
        val result = repository.getALlFavourite()
        assertEquals(0, result.size)
    }

    @Test
    fun `test is Country Favourite true`() = runBlocking {
        coEvery { dao.isFav("Spain") }.returns(CountryData())
        val result = repository.isCountryFav("Spain")
        assertNotNull(result)
    }

    @Test
    fun `test is Country Favourite false`() = runBlocking {
        coEvery { dao.isFav("Spain") }.returns(null)
        val result = repository.isCountryFav("Spain")
        assertNull(result)
    }

}