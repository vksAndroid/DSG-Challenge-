package countryinfo.app.data.model

import com.google.gson.annotations.SerializedName


data class FloatFacets(

    @SerializedName("identifier") var identifier: String = "",
    @SerializedName("stringValue") var stringValue: String = "",
    @SerializedName("value") var value: Double = 0.0,
    @SerializedName("name") var name: String = ""

)
