package countryinfo.app.data.model

import com.google.gson.annotations.SerializedName

data class DsgPriceIndicators(

    @SerializedName("dealsPercentage") var dealsPercentage: Double = 0.0,
    @SerializedName("mapPriceIndicator") var mapPriceIndicator: Int = 0,
    @SerializedName("priceIndicator") var priceIndicator: Int = 0

)
