package countryinfo.app.ui.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import countryinfo.app.uicomponents.CountryBasicDetail
import countryinfo.app.uicomponents.CountryDetailComponent
import countryinfo.app.uicomponents.CountryNameCard
import countryinfo.app.uicomponents.ImageFullFlag
import countryinfo.app.utils.labelCurrencies
import countryinfo.app.utils.labelLanguages
import countryinfo.app.utils.labelPopulation
import countryinfo.app.utils.labelcarDriveSide
import countryinfo.app.vm.CountryListVm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun DetailOverViewTab(cca3: String, viewModel: CountryListVm) {

    val countryList = viewModel.observeCountryList()
    val countryDetails =
        countryList.value.first { countryDetailItem -> cca3 == countryDetailItem.cca3 }

    Scaffold(backgroundColor = Color.White,
        content = {
                itemPadding->
            ConstraintLayout(setComponentsUsingConstraints(), modifier = Modifier
                .fillMaxSize()
                .padding(bottom = itemPadding.calculateBottomPadding() + 40.dp)
                .verticalScroll(
                    rememberScrollState()
                )) {

                ImageFullFlag(flagImageUrl = countryDetails.flags?.png!!)

                CountryNameCard(title = countryDetails.name?.common!!, value =countryDetails.name?.official!! )

                CountryBasicDetail(countryDetails)

                CountryDetailComponent(title = labelLanguages, value = countryDetails.languages)
                CountryDetailComponent(title = labelCurrencies, value = countryDetails.currencies)
                CountryDetailComponent(title = labelPopulation, value = countryDetails.population.toString())
                CountryDetailComponent(title = labelcarDriveSide, value = countryDetails.car?.side!!, isDriverItem = true)
                CountryDetailComponent(title = "Timezone(s)", value = countryDetails.timezones)
                CountryDetailComponent(isImage = true, imageUrl = countryDetails.coatOfArms?.png!!, title = "Coat of Arms", value = "")

            }
        })
}

fun setComponentsUsingConstraints(): ConstraintSet {

    return ConstraintSet {

        val idTopFlag = createRefFor("top_flag")
        val idBasicDetail = createRefFor("BasicDetail")
        val idCountry = createRefFor("Country")

        val idLanguages = createRefFor("Languages")
        val idTimeZone = createRefFor("Timezone(s)")
        val idCurrencies = createRefFor("Currencies")
        val idPopulation = createRefFor("Population")
        val idDriverSide = createRefFor("Car Driver Side")
        val idCoatOfArm = createRefFor("Coat of Arms")

        constrain(idTopFlag) {
            top.linkTo(parent.top, margin = 0.dp)
        }
        constrain(idBasicDetail) {
            top.linkTo(idCountry.bottom, margin = 10.dp)
        }
        constrain(idCountry) {
            bottom.linkTo(idTopFlag.bottom)
            top.linkTo(idTopFlag.bottom)
            start.linkTo(parent.start, margin = 12.dp)
        }

        constrain(idTimeZone) {
            top.linkTo(idBasicDetail.bottom)
        }
        constrain(idPopulation) {
            start.linkTo(idTimeZone.end)
            top.linkTo(idTimeZone.top)
            bottom.linkTo(idTimeZone.bottom)

            height = Dimension.fillToConstraints
        }
        constrain(idLanguages) {
            top.linkTo(idTimeZone.bottom)
            height = Dimension.fillToConstraints
        }
        constrain(idCurrencies) {
            start.linkTo(idLanguages.end)
            bottom.linkTo(idLanguages.bottom)
            top.linkTo(idLanguages.top)
            height = Dimension.fillToConstraints
        }

        constrain(idDriverSide) {
            top.linkTo(idLanguages.bottom)
            bottom.linkTo(idCoatOfArm.bottom)
            height = Dimension.fillToConstraints

        }
        constrain(idCoatOfArm) {
            start.linkTo(idDriverSide.end)
            top.linkTo(idLanguages.bottom)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //DetailOverViewTab("HKG",null)
}
