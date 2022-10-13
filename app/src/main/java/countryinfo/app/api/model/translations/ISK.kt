package countryinfo.app.api.model.translations

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class ISK (

    @SerializedName("name"   ) var name   : String? = null,
    @SerializedName("symbol" ) var symbol : String? = null

)
