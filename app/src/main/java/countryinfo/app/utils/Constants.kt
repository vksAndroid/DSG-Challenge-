package countryinfo.app.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import java.text.NumberFormat
import java.util.*

const val EMPTY_STRING = ""

// Navigation Routes
const val RouteOverview = "overview"
const val RouteMap = "map"
const val RouteSearch = "search"
const val RouteSaved = "saved"

//Title Names for App Bar
const val titleOverview = "Overview"
const val titleMap = "Maps"
const val titleSearch = "Search"
const val titleSaved = "Saved"
const val titleCountries = "Countries"

// Content Description
const val contentDescriptionCoatImage = "Coat Image of Country"
const val contentDescriptionSaveCountry = "Save Country"
const val contentDescriptionDriverSide = "Driver Side"

// Time Duration Values
const val duration_1000 = 1000

// Ids for constraint layout

const val idTopFlag = "top_flag"
const val idCountry = "country"
const val idBasicDetail = "basic_detail"
const val idMapColumn = "map_column"
const val idLanguage = "Languages"
const val idCurrencies = "Currencies"
const val idPopulation = "Population"
const val idTimezone = "Timezone(s)"
const val idCoatOfArm = "Coat of Arms"
const val idCarDriverSide = "Car Driver Side"





sealed class ScreenOptions {

    object SearchScreen : ScreenOptions()
    object DetailScreen : ScreenOptions()

}


fun Long?.formatWithComma(): String =
    NumberFormat.getNumberInstance(Locale.US).format(this ?: 0)

