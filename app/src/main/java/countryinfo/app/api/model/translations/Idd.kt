package countryinfo.app.api.model.translations

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Idd (

    @SerializedName("root"     ) var root     : String?           = null,
    @SerializedName("suffixes" ) var suffixes : ArrayList<String> = arrayListOf()

)
