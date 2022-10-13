package countryinfo.app.vm

import countryinfo.app.api.ApiInterface
import countryinfo.app.repo.CountryListRepo
import io.mockk.mockk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class CountryListVmTest {


    val mockApiInterface = mockk<ApiInterface>()

    val repo : CountryListRepo = mockk(relaxed = true)
    val vm : CountryListVm = CountryListVm(repo, StandardTestDispatcher())

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun getCountryList() = runTest{
        vm.getCountryList()
        vm.observeCountryList().collectLatest {  }

    }


    @Test
    fun observeCountryList() {
    }

    @Test
    fun testGetCountryList() {
    }
}