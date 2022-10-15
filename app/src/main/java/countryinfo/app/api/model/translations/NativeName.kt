package countryinfo.app.api.model.translations

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class NativeName(

    @SerializedName("isl") var isl: Isl? = Isl()

)
