package countryinfo.app.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import countryinfo.app.R
import countryinfo.app.presentation.graph.BottomTab
import countryinfo.app.presentation.vm.DsgShopVm
import countryinfo.app.uicomponents.DsgSearchComponent
import countryinfo.app.uicomponents.scaffold_comp.getDP

@Composable
fun HomeShopTab(viewModel: DsgShopVm, title: (String) -> Unit) {

    val getDsgData = viewModel.observeDsgList().collectAsState()
    val query = viewModel.searchQuery().collectAsState().value
    title(BottomTab.TabDsgSearch.title)

    LaunchedEffect(key1 = query) {
        viewModel.searchByDebounce(query)
        if (query.isEmpty()) {
            viewModel.clearSearch()
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = getDP(dimenKey = R.dimen.dp_20))
        ) {

            DsgSearchComponent(query = query, isFocus = true,{
                viewModel.updateSearchQuery(it)
            }){
                viewModel.convertSpeechToText()
            }

            Text(
                text = getDsgData.value, color = Color.Black,
                modifier = Modifier.padding(getDP(dimenKey = R.dimen.dp_12)).verticalScroll(rememberScrollState())
            )

        }
    }
}
