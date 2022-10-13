package countryinfo.app.api.model

import com.google.gson.annotations.SerializedName
import countryinfo.app.api.model.translations.Idd
import countryinfo.app.api.model.translations.Name

data class CountryData(

    @SerializedName("name"         ) var name         : Name?             = Name(),
    @SerializedName("tld"          ) var tld          : ArrayList<String> = arrayListOf(),
    @SerializedName("cca2"         ) var cca2         : String?           = null,
    @SerializedName("ccn3"         ) var ccn3         : String?           = null,
    @SerializedName("cca3"         ) var cca3         : String?           = null,
    @SerializedName("cioc"         ) var cioc         : String?           = null,
    @SerializedName("independent"  ) var independent  : Boolean?          = null,
    @SerializedName("status"       ) var status       : String?           = null,
    @SerializedName("unMember"     ) var unMember     : Boolean?          = null,
//    @SerializedName("currencies"   ) var currencies   : Currencies?       = Currencies(),
    @SerializedName("currencies"   ) var currencies: Map<String, CurrenciesName> = mapOf(),
    @SerializedName("idd"          ) var idd          : Idd?              = Idd(),
    @SerializedName("capital"      ) var capital      : ArrayList<String> = arrayListOf(),
    @SerializedName("altSpellings" ) var altSpellings : ArrayList<String> = arrayListOf(),
    @SerializedName("region"       ) var region       : String?           = null,
    @SerializedName("subregion"    ) var subregion    : String?           = null,
    @SerializedName("languages"    ) var languages: Map<String, String> = mapOf(),
//    @SerializedName("languages"    ) var languages    : Languages?        = Languages(),
    @SerializedName("translations" ) var translations : Translations?     = Translations(),
    @SerializedName("latlng"       ) var latlng       : ArrayList<Double>    = arrayListOf(),
    @SerializedName("landlocked"   ) var landlocked   : Boolean?          = null,
    @SerializedName("area"         ) var area         : Double?              = null,
    @SerializedName("demonyms"     ) var demonyms     : Demonyms?         = Demonyms(),
    @SerializedName("flag"         ) var flag         : String?           = null,
    @SerializedName("maps"         ) var maps         : Maps?             = Maps(),
    @SerializedName("population"   ) var population   : Int?              = null,
    @SerializedName("gini"         ) var gini         : Gini?             = Gini(),
    @SerializedName("fifa"         ) var fifa         : String?           = null,
    @SerializedName("car"          ) var car          : Car?              = Car(),
    @SerializedName("timezones"    ) var timezones    : ArrayList<String> = arrayListOf(),
    @SerializedName("continents"   ) var continents   : ArrayList<String> = arrayListOf(),
    @SerializedName("flags"        ) var flags        : Flags?            = Flags(),
    @SerializedName("coatOfArms"   ) var coatOfArms   : CoatOfArms?       = CoatOfArms(),
    @SerializedName("startOfWeek"  ) var startOfWeek  : String?           = null,
    @SerializedName("capitalInfo"  ) var capitalInfo  : CapitalInfo?      = CapitalInfo(),
    @SerializedName("postalCode"   ) var postalCode   : PostalCode?       = PostalCode()

)