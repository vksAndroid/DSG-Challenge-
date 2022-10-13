package countryinfo.app.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CapitalInfo(
    @Expose(serialize = true, deserialize = true)
    @SerializedName("latlng") var latlng: List<Double> = listOf()
)
