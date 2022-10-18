package countryinfo.app.uicomponents.scaffold_comp

import androidx.compose.runtime.Composable
import countryinfo.app.uicomponents.TopBar
import countryinfo.app.utils.ScreenOptions

/**
 * Render Top bar on the basic of current Screen
 * @param title Navigation Controller to handle Routes or Back Stack
 * @param screenOptions Which screen is currently Active Home or Detail
 * @param isSaved Check wether country is saved or not in Database
 * @param onSavePress on Country Save click Action button to update values
 * @param onBackPress Navigation Back Press Action
 */

@Composable
fun DsgTopBar(
    title: String,
    screenOptions: ScreenOptions,
    isSaved: Boolean = false,
    onSavePress: () -> Unit,
    onBackPress: () -> Unit
) {

    when (screenOptions) {

        ScreenOptions.SearchScreen -> {
            TopBar(title = title, onFavClick = {}) {}
        }

        ScreenOptions.DetailScreen -> {
            TopBar(
                isShowNavigation = true,
                isShowSaveIcon = true,
                isSaved = isSaved,
                title = title, onFavClick = {
                    onSavePress.invoke()
                }) {
                onBackPress.invoke()
            }
        }
    }
}