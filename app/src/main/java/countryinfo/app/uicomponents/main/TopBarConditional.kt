package countryinfo.app.uicomponents.main

import androidx.compose.runtime.Composable
import countryinfo.app.uicomponents.TopBar
import countryinfo.app.utils.ScreenOptions

@Composable
fun TopBarConditional(
    title: String,
    bar: ScreenOptions,
    isSaved: Boolean = false,
    onSavePress: () -> Unit,
    onBackPress: () -> Unit
) {

    when (bar) {

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