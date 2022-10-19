package countryinfo.app.data.model.translations

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Name(

    @SerializedName("common") var common: String = "",
    @SerializedName("official") var official: String = ""

)
