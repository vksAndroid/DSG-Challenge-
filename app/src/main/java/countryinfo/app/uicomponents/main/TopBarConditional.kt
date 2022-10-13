package countryinfo.app.uicomponents.main

import androidx.compose.runtime.Composable
import countryinfo.app.uicomponents.TopBar
import countryinfo.app.utils.WhichComponent

@Composable
fun TopBarConditional(title : String,
                      bar : WhichComponent,
                      onSavePress : ()->Unit,
                      onBackPress : ()->Unit) {

    when(bar){

        WhichComponent.SearchScreen->{
            TopBar( title = title){
            }
        }

        WhichComponent.DetailScreen->{
            TopBar(
                isShowNavigation = true,
                isShowSaveIcon = true,
                title = title){//countryDetails.name?.common ?: "",) {
                onBackPress.invoke()
            }
        }
    }
}