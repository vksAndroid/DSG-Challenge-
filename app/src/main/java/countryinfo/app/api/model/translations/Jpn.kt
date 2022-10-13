package countryinfo.app.api.model.translations

import com.google.gson.annotations.SerializedName


data class Jpn (

    @SerializedName("official" ) var official : String? = null,
    @SerializedName("common"   ) var common   : String? = null

)
