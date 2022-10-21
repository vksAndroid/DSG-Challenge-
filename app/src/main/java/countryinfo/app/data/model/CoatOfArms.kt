package countryinfo.app.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class CoatOfArms(

    @SerializedName("png") var png: String = "",
    @SerializedName("svg") var svg: String = ""

)
