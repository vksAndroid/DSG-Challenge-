package countryinfo.app.uicomponents.scaffold_comp

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import countryinfo.app.utils.ScreenOptions

/**
* Render Bottom bar on the basic of current Screen
 * @param navController Navigation Controller to handle Routes or Back Stack
 * @param screenOptions Which screen is currently Active Home or Detail
 */
@Composable
fun DsgBottomBar(
    navController: NavController,
    screenOptions: ScreenOptions,
    isCurrentLocationAmerica: Boolean
) {

    when (screenOptions) {

        ScreenOptions.SearchScreen -> {
            DsgBottomMenu(navController, false, isCurrentLocationAmerica)
        }
        ScreenOptions.DetailScreen -> {
            DsgBottomMenu(navController, true)
        }

    }

}
