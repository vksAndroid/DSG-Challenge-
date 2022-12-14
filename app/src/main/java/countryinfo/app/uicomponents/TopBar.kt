package countryinfo.app.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import countryinfo.app.R
import countryinfo.app.theme.ThemeBlue
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.contentDescriptionSaveCountry


@Composable
fun TopBar(
    title: String = "",
    isShowNavigation: Boolean = false,
    isSaved: Boolean = false,
    isShowSaveIcon: Boolean = false,
    onFavClick: () -> Unit,
    clickAction: () -> Unit
) {
    TopAppBar(contentColor = Color.Black,
        title = {
            Text(
                text = title,
                modifier = Modifier.testTag("top_bar")
                    .fillMaxWidth()
                    .padding(end = 64.dp),
                textAlign = if(isShowNavigation)TextAlign.Start else TextAlign.Center
            )
        },

        navigationIcon = {
            if (isShowNavigation)
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier.testTag("back_icon")
                        .padding(getDP(dimenKey = R.dimen.dp_8))
                        .clickable { clickAction.invoke() }
                )
        },
        actions = {
            if (isShowSaveIcon)
                Icon(
                    if (isSaved) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = contentDescriptionSaveCountry,
                    modifier = Modifier.testTag("save_icon")
                        .padding(8.dp)
                        .clickable { onFavClick.invoke() },
                    tint = ThemeBlue
                )
        }
    )
}
