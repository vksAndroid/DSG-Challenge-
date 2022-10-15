package countryinfo.app.api.model.translations

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Currencies(

    @SerializedName("ISK") var ISK: ISK? = ISK()

)
