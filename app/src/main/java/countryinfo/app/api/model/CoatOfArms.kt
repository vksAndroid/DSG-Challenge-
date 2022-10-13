package countryinfo.app.api.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class CoatOfArms (

    @SerializedName("png" ) var png : String? = null,
    @SerializedName("svg" ) var svg : String? = null

)
