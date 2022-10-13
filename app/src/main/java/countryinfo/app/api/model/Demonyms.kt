package countryinfo.app.api.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import countryinfo.app.api.model.translations.*

@Entity
data class Demonyms (

    

    @SerializedName("isl" ) var isl : Isl? = Isl(),
    @SerializedName("ara" ) var ara : Ara = Ara(),
    @SerializedName("bre" ) var bre : Bre = Bre(),
    @SerializedName("ces" ) var ces : Ces? = Ces(),
    @SerializedName("cym" ) var cym : Cym = Cym(),
    @SerializedName("deu" ) var deu : Deu? = Deu(),
    @SerializedName("eng" ) var eng : Eng? = Eng(),
    @SerializedName("est" ) var est : Est? = Est(),
    @SerializedName("fin" ) var fin : Fin? = Fin(),
    @SerializedName("fra" ) var fra : Fra? = Fra(),
    @SerializedName("hrv" ) var hrv : Hrv? = Hrv(),
    @SerializedName("hun" ) var hun : Hun? = Hun(),
    @SerializedName("idd" ) var idd : Idd? = Idd(),
    @SerializedName("isk" ) var isk : ISK? = ISK(),
    @SerializedName("ita" ) var ita : Ita? = Ita(),
    @SerializedName("kor" ) var kor : Kor? = Kor(),
    @SerializedName("nid" ) var nid : Nld? = Nld(),
    @SerializedName("per" ) var per : Per? = Per(),
    @SerializedName("pol" ) var pol : Pol? = Pol(),
    @SerializedName("por" ) var por : Por? = Por(),
    @SerializedName("rus" ) var rus : Rus? = Rus(),
    @SerializedName("slk" ) var slk : Slk? = Slk(),
    @SerializedName("spa" ) var spa : Spa? = Spa(),
    @SerializedName("swe" ) var swe : Swe? = Swe(),
    @SerializedName("tur" ) var tur : Tur? = Tur(),
    @SerializedName("Urd" ) var urd : Urd? = Urd(),
    @SerializedName("zho" ) var zho : Zho? = Zho()

)