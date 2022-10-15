package countryinfo.app.api.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class PostalCode(

    @SerializedName("format") var format: String? = null,
    @SerializedName("regex") var regex: String? = null

)
