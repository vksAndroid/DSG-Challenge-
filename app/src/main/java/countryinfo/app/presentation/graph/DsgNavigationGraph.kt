package countryinfo.app.presentation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import countryinfo.app.presentation.screens.detail.CountryMapScreen
import countryinfo.app.presentation.screens.detail.DetailOverViewTab
import countryinfo.app.presentation.screens.home.HomeSavedTab
import countryinfo.app.presentation.screens.home.HomeSearchTab
import countryinfo.app.presentation.screens.home.HomeShopTab
import countryinfo.app.presentation.vm.CountryListVm
import countryinfo.app.presentation.vm.DsgShopVm
import countryinfo.app.utils.titleSaved
import countryinfo.app.utils.titleSearch

@Composable
fun DsgNavigationGraph(
    navController: NavHostController,
    viewModel: CountryListVm,
    shopViewModel: DsgShopVm,
    route: String
) {

    NavHost(
        navController = navController,
        startDestination = route
    ) {

        composable(BottomTab.TabSearch.route) {
            HomeSearchTab(
                navController = navController,
                viewModel = viewModel
            )
            viewModel.selectedTab.value = titleSearch
        }
        composable(BottomTab.TabSaved.route) {
            HomeSavedTab(
                navController = navController,
                viewModel = viewModel
            )
            viewModel.selectedTab.value = titleSaved

        }

        composable(
            route = BottomTab.TabOverview.route
        ) {
            DetailOverViewTab(viewModel = viewModel)

        }
        composable(BottomTab.TabMap.route) {
            CountryMapScreen(
                viewModel = viewModel
            )

        }

        composable(BottomTab.TabDsgSearch.route) {
            HomeShopTab(
                viewModel = shopViewModel
            ) {viewModel.title.value = it}

        }

    }
}