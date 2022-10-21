package countryinfo.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import countryinfo.app.data.model.translations.Name
import org.jetbrains.annotations.NotNull

@Entity(tableName = "countries")
data class CountryData(
    @SerializedName("name") var name: Name = Name(),
    @PrimaryKey @NotNull @SerializedName("cca3") var cca3: String = "",
    @SerializedName("independent") var independent: Boolean = false,
    @SerializedName("currencies") var currencies: Map<String, CurrenciesName> = mapOf(),
    @SerializedName("capital") var capital: ArrayList<String> = arrayListOf(),
    @SerializedName("region") var region: String = "",
    @SerializedName("subregion") var subregion: String = "",
    @SerializedName("languages") var languages: Map<String, String> = mapOf(),
    @SerializedName("latlng") var latlng: ArrayList<Double> = arrayListOf(),
    @SerializedName("flag") var flag: String = "",
    @SerializedName("population") var population: Int = 0,
    @SerializedName("car") var car: Car = Car(),
    @SerializedName("timezones") var timezones: ArrayList<String> = arrayListOf(),
    @SerializedName("flags") var flags: Flags = Flags(),
    @SerializedName("coatOfArms") var coatOfArms: CoatOfArms? = CoatOfArms(),
    @SerializedName("capitalInfo") var capitalInfo: CapitalInfo? = CapitalInfo(),

)