package countryinfo.app.uicomponents.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import countryinfo.app.uicomponents.BottomMenu
import countryinfo.app.utils.ScreenOptions

@Composable
fun BottomBarConditional(navController: NavController,bar : ScreenOptions) {

    when(bar){

        ScreenOptions.SearchScreen->{
            BottomMenu(navController, false)
        }
        ScreenOptions.DetailScreen->{
            BottomMenu(navController, true)
        }

    }

}