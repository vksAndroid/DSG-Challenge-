package countryinfo.app.api.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Gini (

    @SerializedName("2017" ) var name : Double? = null

)
