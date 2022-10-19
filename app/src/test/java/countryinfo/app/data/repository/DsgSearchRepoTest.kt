package countryinfo.app.data.repository

import countryinfo.app.data.ApiInterface
import countryinfo.app.utils.ApiResult
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import retrofit2.Response
import java.net.URLEncoder

class DsgSearchRepoTest {

    private val apiService = mockk<ApiInterface>()
    private val urlencodeMok: URLEncoder = mockk()
    val dsgRepo = DsgSearchRepo(apiService)


    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test dsg search api return success`() = runBlocking {
        val responseMock: Response<String> = mockk(relaxed = true)
        coEvery { apiService.getDsgSearchByName("https://prod-catalog-product-api.dickssportinggoods.com/v2/search?searchVO=%7B%22searchTerm%22%3A%22wilson%22%2C%22pageNumber%22%3A%220%22%2C%22pageSize%22%3A%2210%22%7D") } returns responseMock
        every { responseMock.isSuccessful } returns true
        every { responseMock.body() } returns "abcdefgh"
        dsgRepo.getSearchData("wilson").collect {
            when (it) {
                is ApiResult.Success -> {
                    Assertions.assertEquals("abcdefgh", it.value)
                    Assertions.assertTrue(true)
                }
                is ApiResult.Failure -> {
                    Assertions.assertTrue(false)
                }
                is ApiResult.Loading -> {
                    Assertions.assertTrue(true)
                }
                else -> {
                    Assertions.assertTrue(true)
                }
            }
        }
    }

    @Test
    fun `test dsg search api return failure`() = runBlocking {
        val responseMock: Response<String> = mockk(relaxed = true)
        coEvery { apiService.getDsgSearchByName("https://prod-catalog-product-api.dickssportinggoods.com/v2/search?searchVO=%7B%22searchTerm%22%3A%22wilson%22%2C%22pageNumber%22%3A%220%22%2C%22pageSize%22%3A%2210%22%7D") } returns responseMock
        every { responseMock.isSuccessful } returns false

        val responseBodyMock = mockk<ResponseBody>()
        every { responseMock.message() }.returns("Error occurred")
        every { responseMock.errorBody() }.returns(responseBodyMock)

        dsgRepo.getSearchData("wilson").collect {
            when (it) {
                is ApiResult.Success -> {
                    Assertions.assertTrue(false)
                }
                is ApiResult.Failure -> {
                    Assertions.assertEquals(it.message, "Error occurred")
                    Assertions.assertTrue(true)
                }
                is ApiResult.Loading -> {
                    Assertions.assertTrue(true)
                }
                else -> {
                    Assertions.assertTrue(true)
                }
            }
        }
    }
}