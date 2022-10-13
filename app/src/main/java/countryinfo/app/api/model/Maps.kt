package countryinfo.app.api.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Maps(

    @SerializedName("googleMaps") var googleMaps: String? = null,
    @SerializedName("openStreetMaps") var openStreetMaps: String? = null

)
