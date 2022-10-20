package countryinfo.app.data.model

import com.google.gson.annotations.SerializedName

data class ProductVOs(

    @SerializedName("catentryId") var catentryId: Int = 0,
    @SerializedName("partnumber") var partnumber: String = "",
    @SerializedName("parentPartnumber") var parentPartnumber: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("mfName") var mfName: String = "",
    @SerializedName("thumbnail") var thumbnail: String = "",
    @SerializedName("fullImage") var fullImage: String = "",
    @SerializedName("assetSeoUrl") var assetSeoUrl: String = "",
    @SerializedName("dsgSeoUrl") var dsgSeoUrl: String = "",
    @SerializedName("dsgProductSortDate") var dsgProductSortDate: String = "",
    //@SerializedName("attributes") var attributes: String = "",
    @SerializedName("floatFacets") var floatFacets: ArrayList<FloatFacets> = arrayListOf(),
    @SerializedName("dsgPriceIndicators") var dsgPriceIndicators: DsgPriceIndicators? = null,
    @SerializedName("ratingValue") var ratingValue: Double = 0.0,
    @SerializedName("ratingCount") var ratingCount: Int = 0,
    @SerializedName("quickViewEnabled") var quickViewEnabled: Boolean = false,
    @SerializedName("isPinned") var isPinned: Boolean = false,
    @SerializedName("isComingSoon") var isComingSoon: Boolean = false


)
