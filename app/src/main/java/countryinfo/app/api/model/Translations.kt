package countryinfo.app.api.model


import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import countryinfo.app.api.model.translations.*

@Entity
data class Translations(

    @SerializedName("ara") var ara: Ara? = Ara(),
    @SerializedName("bre") var bre: Bre? = Bre(),
    @SerializedName("ces") var ces: Ces? = Ces(),
    @SerializedName("cym") var cym: Cym? = Cym(),
    @SerializedName("deu") var deu: Deu? = Deu(),
    @SerializedName("est") var est: Est? = Est(),
    @SerializedName("fin") var fin: Fin? = Fin(),
    @SerializedName("fra") var fra: Fra? = Fra(),
    @SerializedName("hrv") var hrv: Hrv? = Hrv(),
    @SerializedName("hun") var hun: Hun? = Hun(),
    @SerializedName("ita") var ita: Ita? = Ita(),
    @SerializedName("jpn") var jpn: Jpn? = Jpn(),
    @SerializedName("kor") var kor: Kor? = Kor(),
    @SerializedName("nld") var nld: Nld? = Nld(),
    @SerializedName("per") var per: Per? = Per(),
    @SerializedName("pol") var pol: Pol? = Pol(),
    @SerializedName("por") var por: Por? = Por(),
    @SerializedName("rus") var rus: Rus? = Rus(),
    @SerializedName("slk") var slk: Slk? = Slk(),
    @SerializedName("spa") var spa: Spa? = Spa(),
    @SerializedName("swe") var swe: Swe? = Swe(),
    @SerializedName("tur") var tur: Tur? = Tur(),
    @SerializedName("urd") var urd: Urd? = Urd(),
    @SerializedName("zho") var zho: Zho? = Zho()

)
