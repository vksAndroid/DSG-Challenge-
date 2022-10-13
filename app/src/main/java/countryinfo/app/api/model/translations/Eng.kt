package countryinfo.app.api.model.translations

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Eng(

    @SerializedName("f") var f: String? = null,
    @SerializedName("m") var m: String? = null

)
