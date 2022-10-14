package countryinfo.app.utils

const val LOCATION_TYPE_CURRENT = 1
const val LOCATION_TYPE_COUNTRY = 2
const val LOCATION_TYPE_CAPITAL = 3
const val EMPTY_STRING = ""

sealed class ScreenOptions {

    object SearchScreen : ScreenOptions()
    object DetailScreen : ScreenOptions()

}

