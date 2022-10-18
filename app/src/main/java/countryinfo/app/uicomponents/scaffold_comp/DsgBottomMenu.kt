package countryinfo.app.uicomponents.scaffold_comp

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import countryinfo.app.R
import countryinfo.app.presentation.graph.BottomTab
import countryinfo.app.theme.OffWhite


@Composable
fun DsgBottomMenu(
    navController: NavController,
    isDetailScreen: Boolean = true,
    isCurrentLocationAmerica: Boolean = false
) {

    val menuItems = if (isDetailScreen)
        listOf(
            BottomTab.TabOverview,
            BottomTab.TabMap
        )
    else if (isCurrentLocationAmerica) {
        listOf(
            BottomTab.TabSearch,
            BottomTab.TabSaved,
            BottomTab.TabDsgSearch
        )
    } else {
        listOf(
            BottomTab.TabSearch,
            BottomTab.TabSaved
        )
    }

    BottomNavigation(
        modifier = Modifier.testTag("test_bottom_navigation"),
        contentColor = colorResource(id = R.color.white), backgroundColor = OffWhite
    )
    {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        menuItems.forEach {

            BottomNavigationItem(
                modifier = Modifier.testTag("nav_item_${it.title}"),
                label = { Text(text = it.title) },
                alwaysShowLabel = true,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray,
                selected = currentRoute == it.route,
                onClick = {

                    if (currentRoute != it.route) {
                        navController.navigate(it.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = it.imageVector,
                        contentDescription = it.title
                    )
                })
        }
    }
}