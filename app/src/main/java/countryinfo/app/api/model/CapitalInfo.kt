package countryinfo.app.api.model

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class CapitalInfo(
    @SerializedName("latlng") var latlng: List<Double> = listOf()
)
