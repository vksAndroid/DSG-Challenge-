package countryinfo.app.presentation.screens.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import countryinfo.app.R
import countryinfo.app.presentation.vm.CountryListVm
import countryinfo.app.uicomponents.*
import countryinfo.app.utils.*

@Composable
fun DetailOverViewTab(viewModel: CountryListVm) {

    val countryDetail = viewModel.observeCountryData().collectAsState().value

    viewModel.title.value = titleCountries

    ConstraintLayout(
        setComponentsUsingConstraints(), modifier = Modifier
            .testTag("detail_overview_screen")
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(bottom = 12.dp)
    ) {
        ImageFullFlag(flagImageUrl = countryDetail.flags.png)

        CountryNameCard(
            title = countryDetail.name.common,
            value = countryDetail.name.official
        )

        CountryBasicDetail(countryDetail)

        CountryDetailComponent(title = stringResource(id = R.string.languages), ValueComponent(value = countryDetail.languages))

        CountryDetailComponent(title = stringResource(id = R.string.currencies), uiComp = RenderCurrency(map = countryDetail.currencies))

        countryDetail.car.side.let {
            CountryDetailComponent(title = stringResource(id = R.string.car_driver_side), uiComp = DriveSideComponent(it))
        }

        CountryDetailComponent(title = stringResource(id = R.string.population), uiComp = RenderText(countryDetail.population.toString()))

        CountryDetailComponent(title = stringResource(id = R.string.timezones), uiComp = RenderList(list = countryDetail.timezones))

        countryDetail.coatOfArms?.png?.let {
                    CountryDetailComponent(
                        title = stringResource(id = R.string.coat_of_arms),
                        uiComp = ImageCoatOfArm(it)
                    )
        }

    }

}

fun setComponentsUsingConstraints(): ConstraintSet {

    return ConstraintSet {

        val idTopFlag = createRefFor(idTopFlag)
        val idBasicDetail = createRefFor(idBasicDetail)
        val idCountry = createRefFor(idCountry)

        val idLanguages = createRefFor(idLanguage)
        val idTimeZone = createRefFor(idTimezone)
        val idCurrencies = createRefFor(idCurrencies)
        val idPopulation = createRefFor(idPopulation)
        val idDriverSide = createRefFor(idCarDriverSide)
        val idCoatOfArm = createRefFor(idCoatOfArm)

        constrain(idTopFlag) {
            top.linkTo(parent.top, margin = 0.dp)
        }
        constrain(idBasicDetail) {
            top.linkTo(idCountry.bottom, margin = 24.dp)
        }
        constrain(idCountry) {
            bottom.linkTo(idTopFlag.bottom)
            top.linkTo(idTopFlag.bottom)
            start.linkTo(parent.start, margin = 12.dp)
        }

        constrain(idTimeZone) {
            top.linkTo(idBasicDetail.bottom, margin = 10.dp)
        }
        constrain(idPopulation) {
            start.linkTo(idTimeZone.end)
            top.linkTo(idTimeZone.top)
            bottom.linkTo(idTimeZone.bottom)

            height = Dimension.fillToConstraints
        }
        constrain(idLanguages) {
            top.linkTo(idTimeZone.bottom, margin = 10.dp)
            height = Dimension.fillToConstraints
        }
        constrain(idCurrencies) {
            start.linkTo(idLanguages.end)
            bottom.linkTo(idLanguages.bottom)
            top.linkTo(idLanguages.top)
            height = Dimension.fillToConstraints
        }

        constrain(idDriverSide) {
            top.linkTo(idLanguages.bottom, margin = 10.dp)
            bottom.linkTo(idCoatOfArm.bottom)
            height = Dimension.fillToConstraints

        }
        constrain(idCoatOfArm) {
            start.linkTo(idDriverSide.end)
            top.linkTo(idLanguages.bottom)
        }
    }
}
