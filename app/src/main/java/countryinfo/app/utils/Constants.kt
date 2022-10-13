package countryinfo.app.utils

public val labeCapital = "Capital"
public val labelRegion = "Region"
public val labelSubRegion = "Subregion"
public val labelLanguages = "Languages"
public val labelCurrencies = "Currencies"
public val labelPopulation = "Population"
public val labelcarDriveSide = "Car Driver Side"
public val labelCoatOfArm = "Coat of Arms"

sealed class WhichComponent {

    object SearchScreen : WhichComponent()
    object DetailScreen : WhichComponent()

}

