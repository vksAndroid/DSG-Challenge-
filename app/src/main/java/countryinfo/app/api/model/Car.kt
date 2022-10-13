package countryinfo.app.api.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Car (

    @SerializedName("signs" ) var signs : ArrayList<String> = arrayListOf(),
    @SerializedName("side"  ) var side  : String?           = null

)
