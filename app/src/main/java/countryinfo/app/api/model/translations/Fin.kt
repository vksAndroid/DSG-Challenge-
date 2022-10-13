package countryinfo.app.api.model.translations

import com.google.gson.annotations.SerializedName


data class Fin (

    @SerializedName("official" ) var official : String? = null,
    @SerializedName("common"   ) var common   : String? = null

)
