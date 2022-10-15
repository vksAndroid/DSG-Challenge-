package countryinfo.app

import android.os.SystemClock
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import countryinfo.app.ui.screens.HomeScreen
import countryinfo.app.ui.theme.CountryInfoTheme
import countryinfo.app.utils.LocationPermission
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_NETWORK_STATE
    )

    @Before
    fun setup() {
        launchSearchScreen()
    }

    @Test
    fun test_all_screens_are_visible_with_components() {
        with(composeTestRule) {
            onNodeWithTag("top_bar", true).assertIsDisplayed()
            onNodeWithTag("country_search_text_field").assertIsDisplayed()
            onNodeWithTag("shimmer_effect").assertIsDisplayed()
            onNodeWithTag("test_bottom_navigation", true).assertIsDisplayed()
            val countryLazyColumn = composeTestRule.onAllNodesWithTag("country_lazy_column", true)
            waitUntil(20000) { countryLazyColumn.fetchSemanticsNodes().isEmpty() }
            onNodeWithTag("test_bottom_navigation").assertIsDisplayed()
            SystemClock.sleep(20000)
            onAllNodesWithTag("country_item_view").onFirst().assertIsDisplayed()
            onRoot().performTouchInput {
                swipeUp(
                    startY = 600F,
                    endY = 100F,
                    durationMillis = 5000
                )
            }
            onNodeWithTag("country_lazy_column", true).onChildAt(2).performClick()
            val saveIcon = composeTestRule.onNodeWithTag("save_icon")
            saveIcon.assertIsDisplayed()
            onNodeWithTag("detail_overview_screen").assertIsDisplayed()
            onNodeWithTag("image_full_flag").assertIsDisplayed()
            onNodeWithTag("country_name_card").assertIsDisplayed()
            onNodeWithTag("country_basic_details_card").assertIsDisplayed()
            val savedScreenIconToClick =
                onNodeWithTag("test_bottom_navigation", true).assertIsDisplayed()
            savedScreenIconToClick.onChildAt(0).onChildAt(1).performClick()
            SystemClock.sleep(2000)
            val countryMapScreen = onNodeWithTag("country_map_screen", true)
            countryMapScreen.assertIsDisplayed()
            onRoot().performTouchInput {
                swipeUp(
                    startY = 600F,
                    endY = 100F,
                    durationMillis = 5000
                )
            }
            onAllNodesWithTag("map_view").onFirst().assertIsDisplayed()
            SystemClock.sleep(2000)
        }
    }

    @Test
    fun test_item_is_successfully_saved() {
        with(composeTestRule) {
            val countryLazyColumn = composeTestRule.onAllNodesWithTag("country_lazy_column", true)
            waitUntil(20000) { countryLazyColumn.fetchSemanticsNodes().isEmpty() }
            onNodeWithTag("test_bottom_navigation").assertIsDisplayed()
            SystemClock.sleep(20000)
            val countryLazyColumnAppeared = onNodeWithTag("country_lazy_column", true)
            countryLazyColumnAppeared.onChildAt(2).performClick()
            composeTestRule.onNodeWithTag("save_icon").performClick()
            composeTestRule.onNodeWithTag("back_icon").performClick()
            val savedScreenIconToClick =
                onNodeWithTag("test_bottom_navigation", true).assertIsDisplayed()
            savedScreenIconToClick.onChildAt(0).onChildAt(1).performClick()
            onNodeWithTag("home_saved_screen").assertIsDisplayed()
        }
    }

    @Test
    fun test_saved_item_is_successfully_removed() {
        with(composeTestRule) {
            val countryLazyColumn = composeTestRule.onAllNodesWithTag("country_lazy_column", true)
            waitUntil(20000) { countryLazyColumn.fetchSemanticsNodes().isEmpty() }
            onNodeWithTag("test_bottom_navigation").assertIsDisplayed()
            SystemClock.sleep(20000)
            val countryLazyColumnAppeared = onNodeWithTag("country_lazy_column", true)
            countryLazyColumnAppeared.onChildAt(0).performClick()
            composeTestRule.onNodeWithTag("save_icon").performClick()
            composeTestRule.onNodeWithTag("back_icon").performClick()
            val savedScreenIconToClick =
                onNodeWithTag("test_bottom_navigation", true).assertIsDisplayed()
            savedScreenIconToClick.onChildAt(0).onChildAt(1).performClick()
            onNodeWithTag("home_saved_screen").assertIsDisplayed()
            SystemClock.sleep(2000)
            val savedCountryLazyColumnAppeared = onNodeWithTag("country_lazy_column", true)
            savedCountryLazyColumnAppeared.onChildAt(0).performClick()
            composeTestRule.onNodeWithTag("save_icon").performClick()
            composeTestRule.onNodeWithTag("back_icon").performClick()
            savedScreenIconToClick.onChildAt(0).onChildAt(1).performClick()
        }
    }

    @Test
    fun test_search_country() {
        with(composeTestRule) {
            val countryLazyColumn = composeTestRule.onAllNodesWithTag("country_lazy_column", true)
            waitUntil(20000) { countryLazyColumn.fetchSemanticsNodes().isEmpty() }
            onNodeWithTag("test_bottom_navigation").assertIsDisplayed()
            SystemClock.sleep(20000)
            val searchField = onNodeWithTag("country_search_text_field")
            searchField.performTextInput("Ind")
            waitUntil(20000) { countryLazyColumn.fetchSemanticsNodes().isEmpty() }
            SystemClock.sleep(20000)
            searchField.performTextInput("India")
            waitUntil(20000) { countryLazyColumn.fetchSemanticsNodes().isEmpty() }
            SystemClock.sleep(20000)
            onNodeWithTag("country_lazy_column").assertIsDisplayed()
        }
    }

    private fun launchSearchScreen() {
        composeTestRule.activity.setContent {
            LocationPermission()
            CountryInfoTheme {
                HomeScreen()
            }
        }
    }

}