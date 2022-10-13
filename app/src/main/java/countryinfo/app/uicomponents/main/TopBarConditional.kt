package countryinfo.app.uicomponents.main

import androidx.compose.runtime.Composable
import countryinfo.app.uicomponents.TopBar
import countryinfo.app.utils.ScreenOptions

@Composable
fun TopBarConditional(title : String,
                      bar : ScreenOptions,
                      onSavePress : ()->Unit,
                      onBackPress : ()->Unit) {

    when(bar){

        ScreenOptions.SearchScreen->{
            TopBar( title = title){
            }
        }

        ScreenOptions.DetailScreen->{
            TopBar(
                isShowNavigation = true,
                isShowSaveIcon = true,
                title = title){
                onBackPress.invoke()
            }
        }
    }
}