package countryinfo.app.vm

import countryinfo.app.api.ApiInterface
import countryinfo.app.repo.CountryListRepo
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

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
    fun getCountryList() = runTest {


    }


    @Test
    fun observeCountryList() {
    }

    @Test
    fun testGetCountryList() {
    }
}