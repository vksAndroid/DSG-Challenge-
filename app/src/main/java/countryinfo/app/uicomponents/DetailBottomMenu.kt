package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import countryinfo.app.R
import countryinfo.app.utils.tabs.BottomTab



@Composable
fun BottomMenu(navController: NavController,
               isDetailScreen : Boolean = true) {

    val menuItems = if(isDetailScreen)
        listOf(
        BottomTab.TabOverview,
        BottomTab.TabMap
    )
    else
    listOf(
        BottomTab.TabSearch,
        BottomTab.TabSaved
    )

    BottomNavigation(contentColor = colorResource(id = R.color.white))
    {

         val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        menuItems.forEach {

            BottomNavigationItem(
                label = { Text(text = it.title) },
                alwaysShowLabel = true,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                selected = currentRoute == it.route,
                onClick = {
                    navController.navigate(it.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = it.imageVector,
                        contentDescription = it.title
                    )
                },

                )

        }
    }
}