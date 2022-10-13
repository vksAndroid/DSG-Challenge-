package countryinfo.app.api.model.translations

import com.google.gson.annotations.SerializedName

data class NativeName (

    @SerializedName("isl" ) var isl : Isl? = Isl()

)
