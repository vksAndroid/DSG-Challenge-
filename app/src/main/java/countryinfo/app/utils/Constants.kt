package countryinfo.app.utils

const val EMPTY_STRING = ""

sealed class ScreenOptions {

    object SearchScreen : ScreenOptions()
    object DetailScreen : ScreenOptions()

}

