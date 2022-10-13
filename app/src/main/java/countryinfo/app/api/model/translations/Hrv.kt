package countryinfo.app.api.model.translations

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Hrv(

    @SerializedName("official") var official: String? = null,
    @SerializedName("common") var common: String? = null

)
