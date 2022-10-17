package countryinfo.app.api.model.request

data class SearchVO(
    var searchTerm: String,
    var pageNumber: String = "0", var pageSize: String = "10"
)

