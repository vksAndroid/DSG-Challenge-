package countryinfo.app.uicomponents.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import countryinfo.app.uicomponents.BottomMenu
import countryinfo.app.utils.WhichComponent

@Composable
fun BottomBarConditional(navController: NavController,bar : WhichComponent) {

    when(bar){

        WhichComponent.SearchScreen->{
            BottomMenu(navController, false)
        }
        WhichComponent.DetailScreen->{
            BottomMenu(navController, true)
        }

    }

}