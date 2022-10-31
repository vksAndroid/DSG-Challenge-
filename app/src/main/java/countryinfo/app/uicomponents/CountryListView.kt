package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavController
import countryinfo.app.R
import countryinfo.app.data.model.CountryData
import countryinfo.app.presentation.graph.BottomTab
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.EMPTY_STRING


@Composable
fun CountryListView(
    showShimmer: Boolean = true,
    isConnectedInternet : Boolean = false,
    errorState : State<String>,
    navController: NavController?,
    countryList: List<CountryData> = emptyList(),
    changeState: (countryData: CountryData) -> Unit
) {

    if (countryList.isEmpty() && showShimmer && isConnectedInternet && errorState.value.isEmpty()) {
        LazyColumn(Modifier.testTag("shimmer_effect")) {
            repeat(7) {
                item { LoadingShimmerEffect() }
            }
        }
    } else {

        LazyColumn(state = DsgLazyListState(key = "Home") ,  modifier = Modifier
            .testTag("country_lazy_column")
            .padding(top = getDP(dimenKey = R.dimen.dp_8))) {
            items(items = countryList) { countryData ->

                CountryItemView(
                    commonName = countryData.name.common,
                    officialName = countryData.name.official,
                    capitalName = if (countryData.capital.isNotEmpty()) {
                        countryData.capital[0]
                    } else {
                        EMPTY_STRING
                    },
                    countryFlag = countryData.flags.png,
                    onItemClicked = {
                        changeState.invoke(countryData)

                        navController?.navigate(BottomTab.TabOverview.route)
                    }
                )
            }
        }
    }
}
