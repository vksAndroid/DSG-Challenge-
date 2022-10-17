package countryinfo.app.ui.screens.search

import androidx.compose.runtime.Composable
import countryinfo.app.vm.DsgSearchVm


@Composable
fun DsgSearchTab(vm: DsgSearchVm) {

    vm.search("wilson")
}