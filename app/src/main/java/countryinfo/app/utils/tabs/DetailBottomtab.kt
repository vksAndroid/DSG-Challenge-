package countryinfo.app.utils.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DetailBottomTab (var title : String,
                              var route : String,
                              var imageVector : ImageVector,
                              var isSelected : Boolean) {

    object TabOverview : DetailBottomTab(
        "Overview", "overview",
        Icons.Outlined.Star, false
    )

    object TabMap : DetailBottomTab(
        "Map", "map",
        Icons.Outlined.Map, false
    )
}