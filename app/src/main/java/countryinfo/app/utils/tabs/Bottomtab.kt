package countryinfo.app.utils.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import countryinfo.app.utils.*

sealed class BottomTab(
    var title: String,
    var route: String,
    var imageVector: ImageVector) {

    object TabOverview : BottomTab(
        titleOverview, RouteOverview,
        Icons.Outlined.Star
    )

    object TabMap : BottomTab(
        titleMap, RouteMap,
        Icons.Outlined.Map
    )

    // Country List Bottom Tab
    object TabSearch : BottomTab(
        titleSearch, RouteSearch,
        Icons.Outlined.Search
    )

    object TabSaved : BottomTab(
        titleSaved, RouteSaved,
        Icons.Outlined.StarBorder
    )

    object TabDetails : BottomTab(
        titleCountries, RouteCountryDetail,
        Icons.Outlined.StarBorder
    )

    object TabDsgSearch : BottomTab(
        titleDsgSearch, RouteDsgSearch,
        Icons.Outlined.MapsUgc
    )
}