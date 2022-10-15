package countryinfo.app.api.model

import androidx.room.Entity

@Entity
data class CurrenciesName(val name: String, val symbol: String)