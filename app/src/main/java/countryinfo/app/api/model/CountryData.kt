package countryinfo.app.api.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import countryinfo.app.api.model.translations.Name
import org.jetbrains.annotations.NotNull

@Entity(tableName = "countries")
data class CountryData(
    @SerializedName("name") var name: Name? = Name(),
    @PrimaryKey @NotNull @SerializedName("cca3") var cca3: String = "",
    @SerializedName("independent") var independent: Boolean? = null,
    @SerializedName("currencies") var currencies: Map<String, CurrenciesName> = mapOf(),
    @SerializedName("capital") var capital: ArrayList<String> = arrayListOf(),
    @SerializedName("region") var region: String? = null,
    @SerializedName("subregion") var subregion: String? = null,
    @SerializedName("languages") var languages: Map<String, String> = mapOf(),
    @SerializedName("latlng") var latlng: ArrayList<Double> = arrayListOf(),
    @SerializedName("flag") var flag: String? = null,
    @SerializedName("population") var population: Int? = null,
    @SerializedName("car") var car: Car? = Car(),
    @SerializedName("timezones") var timezones: ArrayList<String> = arrayListOf(),
    @SerializedName("flags") var flags: Flags? = Flags(),
    @SerializedName("coatOfArms") var coatOfArms: CoatOfArms? = CoatOfArms(),
    @SerializedName("capitalInfo") var capitalInfo: CapitalInfo? = CapitalInfo(),

)