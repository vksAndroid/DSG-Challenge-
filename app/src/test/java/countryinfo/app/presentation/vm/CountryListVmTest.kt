package countryinfo.app.presentation.vm

import com.google.android.gms.location.FusedLocationProviderClient
import countryinfo.app.data.model.CountryData
import countryinfo.app.data.repository.CountryListRepo
import countryinfo.app.utils.ApiResult
import countryinfo.app.utils.ConvertSpeechToTextHelper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

@OptIn(ExperimentalCoroutinesApi::class)
class CountryListVmTest {

    private val dispatcher = StandardTestDispatcher()

    private val repo: CountryListRepo = mockk(relaxed = true)
    private val mockedFusedLocation: FusedLocationProviderClient = mockk(relaxed = true)
    private lateinit var vm: CountryListVm
    private val speechToTextHelperMock: ConvertSpeechToTextHelper = mockk(relaxed = true)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        vm = CountryListVm(repo, mockedFusedLocation, speechToTextHelperMock, dispatcher)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `get Country list emit error`() = runTest {
        every { repo.getCountryList() } returns flow { throw Exception("exp") }
        vm.getCountryList()
        Assertions.assertNotNull(vm.observeErrorState().value)
    }

    @Test
    fun `get Country list emit data success`() = runTest {
        val success = ApiResult.Success(
            listOf(
                CountryData(),
                CountryData()
            )
        )
        every { repo.getCountryList() } returns flow {
            emit(success)
        }
        vm.getCountryList()
        delay(500)
        Assertions.assertEquals(2, vm.observeCountryList().value.size)
    }

    @Test
    fun `get Country list by name emit error`() = runTest {
        every { repo.getCountriesByName("abcd") } returns flow { throw Exception("sdfcs") }
        vm.getCountriesByName("abcd")
        Assertions.assertNotNull(vm.observeSearchCountryList().value)
    }


    @Test
    fun `get Country list  by name emit data success`() = runTest {
        val success = ApiResult.Success(
            listOf(
                CountryData(),
                CountryData(),
                CountryData()
            )
        )
        every { repo.getCountriesByName("abcd") } returns flow {
            emit(success)
        }
        vm.getCountriesByName("abcd")
        delay(500)
        Assertions.assertEquals(3, vm.observeSearchCountryList().value.size)
    }


    @Test
    fun `get Country list  by name emit error data`() = runTest {
        val failure = ApiResult.Failure("abcdef", Throwable("message"))
        every { repo.getCountriesByName("abcd") } returns flow {
            emit(failure)
        }
        vm.getCountriesByName("abcd")
        delay(500)
        Assertions.assertEquals(0, vm.observeSearchCountryList().value.size)
        Assertions.assertEquals("abcdef", vm.observeErrorState().value)
    }

    @Test
    fun `test save Favourite`() = runTest {
        val data = CountryData()
        vm.addFavourite(data)
        delay(500)
        Assertions.assertTrue(vm.isFav.value)
    }

    @Test
    fun `test delete Favourite`() = runTest {
        val data = CountryData()
        vm.removeFavourite(data)
        delay(500)
        Assertions.assertFalse(vm.isFav.value)
    }


    @Test
    fun `test all Favourite`() = runTest {
        val data = CountryData()
        coEvery { repo.getALlFavourite() }.answers { listOf(data) }
        vm.getALlFavourite()
        delay(500)
        Assertions.assertEquals(1, vm.observeSavedCountryList().value.size)
    }

    @Test
    fun `test is added to Favourite return true`() = runTest {
        val data = CountryData(cca3 = "abd")
        coEvery { repo.isCountryFav("abcde") }.answers { data }
        vm.isCountryFav("abcde")
        delay(500)
        Assertions.assertTrue(vm.isFav.value)
    }

    @Test
    fun `test is added to Favourite return false`() = runTest {
        coEvery { repo.isCountryFav("abcde") }.answers { null }
        vm.isCountryFav("abcde")
        delay(500)
        Assertions.assertFalse(vm.isFav.value)
    }
}