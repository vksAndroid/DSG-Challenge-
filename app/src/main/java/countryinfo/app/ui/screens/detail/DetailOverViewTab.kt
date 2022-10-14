package countryinfo.app.ui.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
fun DetailOverViewTab(viewModel: CountryListVm) {

    val countryDetail = viewModel.observeCountryData().collectAsState().value

    //countryDetail.name?.common?.let {
    //    viewModel.title.value = it
    //}

    ConstraintLayout(
        setComponentsUsingConstraints(), modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        ImageFullFlag(flagImageUrl = countryDetail.flags?.png!!)

        CountryNameCard(
            title = countryDetail.name?.common!!,
            value = countryDetail.name?.official!!
        )

        CountryBasicDetail(countryDetail)

        countryDetail.languages?.let {
            CountryDetailComponent(title = labelLanguages, value = it)
        }
        countryDetail.currencies?.let {
            CountryDetailComponent(title = labelCurrencies, value = it)
        }

        countryDetail.car?.side?.let {
            CountryDetailComponent(title = labelcarDriveSide, value = it)
        }

        countryDetail.population?.let {
            CountryDetailComponent(title = labelPopulation, value = it)
        }
        countryDetail.timezones?.let {
            CountryDetailComponent(title = "Timezone(s)", value = it)
        }
        countryDetail.coatOfArms?.png?.let {
            CountryDetailComponent(
                isImage = true,
                imageUrl = it,
                title = "Coat of Arms",
                value = ""
            )
        }

    }

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
