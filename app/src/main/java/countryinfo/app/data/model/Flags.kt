package countryinfo.app.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Flags(

    @SerializedName("png") var png: String = "",
    @SerializedName("svg") var svg: String = ""

)
