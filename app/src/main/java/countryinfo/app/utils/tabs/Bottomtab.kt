package countryinfo.app.utils.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomTab(
    var title: String,
    var route: String,
    var imageVector: ImageVector,
    var isSelected: Boolean
) {

    object TabOverview : BottomTab(
        "Overview", "overview",
        Icons.Outlined.Star, false
    )

    object TabMap : BottomTab(
        "Map", "map",
        Icons.Outlined.Map, false
    )

    // Country List Bottom Tab
    object TabSearch : BottomTab(
        "Search", "search",
        Icons.Outlined.Search, false
    )

    object TabSaved : BottomTab(
        "Saved", "saved",
        Icons.Outlined.StarBorder, false
    )
}