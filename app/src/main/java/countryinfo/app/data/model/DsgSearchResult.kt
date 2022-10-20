package countryinfo.app.data.model


import com.google.gson.annotations.SerializedName


data class DsgSearchResult(
    @SerializedName("productVOs") var productVOs: ArrayList<ProductVOs> = arrayListOf(),
)
