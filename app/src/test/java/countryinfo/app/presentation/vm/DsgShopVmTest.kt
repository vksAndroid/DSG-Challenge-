package countryinfo.app.presentation.vm

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.compose.ui.text.intl.Locale
import countryinfo.app.data.model.DsgSearchResult
import countryinfo.app.data.model.ProductVOs
import countryinfo.app.data.repository.DsgSearchRepo
import countryinfo.app.utils.ApiResult
import countryinfo.app.utils.ConvertSpeechToTextHelper
import io.mockk.*
import kotlinx.coroutines.CoroutineDispatcher
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
import java.lang.reflect.Field
import java.lang.reflect.Modifier

@OptIn(ExperimentalCoroutinesApi::class)
class DsgShopVmTest {

    private val mockRepo: DsgSearchRepo = mockk(relaxed = true)
    private val mockGeocoder: Geocoder = mockk(relaxed = true)
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private val speechToTextHelper: ConvertSpeechToTextHelper = mockk(relaxed = true)

    private val dsgvm: DsgShopVm = DsgShopVm(mockRepo, mockGeocoder, speechToTextHelper, dispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test dsg search return success`() = runTest {
        val result = DsgSearchResult(
            productVOs = arrayListOf(
                ProductVOs(name = "Nike"),
                ProductVOs(name = "Adidas")
            )
        )
        val success = ApiResult.Success(result)
        every { mockRepo.getSearchData("wilson", "5") } returns flow {
            emit(success)
        }
        dsgvm.search("wilson", "5")
        delay(500)
        Assertions.assertEquals(result.productVOs.size, dsgvm.observeDsgList().value.size)
    }


    @Test
    fun `test dsg search return failure`() = runTest {
        val failure = ApiResult.Failure("abcdef", Throwable("message"))
        every { mockRepo.getSearchData("wilson", "5") } returns flow {
            emit(failure)
        }
        dsgvm.search("wilson", "5")
        delay(500)
        Assertions.assertEquals("abcdef", dsgvm.observeErrorState().value)
    }


    @Test
    fun `test search by debounce`() = runTest {
        dsgvm.searchByDebounce("india", "5")
    }

    @Test
    fun `test get location for api 33 and above`() = runTest {
        val locationMock: Location = mockk(relaxed = true)
        every { locationMock.latitude } returns 23.55555
        every { locationMock.longitude } returns 24.55555
        setStaticFieldViaReflection(Build.VERSION::class.java.getField("SDK_INT"), 33)

        mockkStatic(Locale::class)


        val address = mockk<Address>()
        every { address.countryName }.answers { "United States" }

        val list = mutableListOf(address)
        val slot = slot<Geocoder.GeocodeListener>()
        every {
            mockGeocoder.getFromLocation(
                23.55555, 23.55555, 1,
                capture(slot)
            )
        } answers {
            //slot.captured.onGeocode(list)
            slot<(MutableList<Address>) -> Unit>().invoke(list)
        }

        dsgvm.getCountryByLocation(locationMock)

    }

    @Test
    fun `test get location for api 33 and below`() = runTest {
        val locationMock: Location = mockk(relaxed = true)
        every { locationMock.latitude } returns 23.55555
        every { locationMock.longitude } returns 24.55555
        setStaticFieldViaReflection(Build.VERSION::class.java.getField("SDK_INT"), 30)

        mockkStatic(Locale::class)


        val address = mockk<Address>()
        every { address.countryName }.answers { "United States" }

        val list = mutableListOf(address)
        every {
            mockGeocoder.getFromLocation(
                23.55555, 23.55555, 1
            )
        } returns list


        dsgvm.getCountryByLocation(locationMock)

    }
}

private fun setStaticFieldViaReflection(field: Field, value: Any) {
    field.isAccessible = true
    Field::class.java.getDeclaredField("modifiers").apply {
        isAccessible = true
        setInt(field, field.modifiers and Modifier.FINAL.inv())
    }
    field.set(null, value)
}
