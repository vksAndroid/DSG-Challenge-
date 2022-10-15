package countryinfo.app.vm

import countryinfo.app.api.ApiInterface
import countryinfo.app.api.model.CountryData
import countryinfo.app.repo.CountryListRepo
import countryinfo.app.utils.ApiResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.capture
import java.lang.Exception
import java.util.concurrent.Flow

internal class CountryListVmTest {


    val mockApiInterface = mockk<ApiInterface>()

    val repo: CountryListRepo = mockk(relaxed = true)
    lateinit var vm: CountryListVm

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        vm = CountryListVm(repo, StandardTestDispatcher())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `get Country List emit error`() = runBlocking {
        every { repo.getCountryList() } returns flow { throw Exception("") }
        vm.getCountryList()
    }

    @Test
    fun getCountryList() = runBlocking {
        every { repo.getCountryList() } returns flow{ emit(ApiResult.Success(listOf(CountryData(),
            CountryData()
        )))}
        vm.getCountryList()
    }


    @Test
    fun observeCountryList() {
    }

    @Test
    fun testGetCountryList() {
    }
}