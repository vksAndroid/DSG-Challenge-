package countryinfo.app.local.dataconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import countryinfo.app.api.model.*
import countryinfo.app.api.model.translations.Idd
import countryinfo.app.api.model.translations.Name
import countryinfo.app.api.model.translations.NativeName
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun toName(name: String): Name {
        return Gson().fromJson(name, Name::class.java)
    }


    @TypeConverter
    fun fromName(name: Name): String {
        return Gson().toJson(name)
    }

    @TypeConverter
    fun fromTld(value: String?): ArrayList<String?>? {
        val listType: Type? = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromTld(list: ArrayList<String?>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toCurrenciesName(name: String): CurrenciesName {
        return Gson().fromJson(name, CurrenciesName::class.java)
    }


    @TypeConverter
    fun fromCurrenciesName(name: CurrenciesName): String {
        return Gson().toJson(name)
    }

    @TypeConverter
    fun toCurrencies(name: String): Map<String, CurrenciesName> {
        val mapType: Type? = object : TypeToken<Map<String, CurrenciesName>?>() {}.type
        return Gson().fromJson(name, mapType)
    }


    @TypeConverter
    fun fromCurrencies(name: Map<String, CurrenciesName>): String {
        return Gson().toJson(name)
    }


    @TypeConverter
    fun toIdd(name: String): Idd {
        return Gson().fromJson(name, Idd::class.java)
    }


    @TypeConverter
    fun fromIdd(name: Idd): String {
        return Gson().toJson(name)
    }

    @TypeConverter
    fun toLanguages(name: String): Map<String, String> {
        val mapType: Type? = object : TypeToken<Map<String, String>?>() {}.type
        return Gson().fromJson(name, mapType)
    }


    @TypeConverter
    fun fromLanguages(name: Map<String, String>): String {
        return Gson().toJson(name)
    }

    @TypeConverter
    fun fromLatLng(value: String?): ArrayList<Double?>? {
        val listType: Type? = object : TypeToken<ArrayList<Double?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromLatLng(list: ArrayList<Double?>?): String? {
        return Gson().toJson(list)
    }


    @TypeConverter
    fun toCar(name: String): Car {
        return Gson().fromJson(name, Car::class.java)
    }

    @TypeConverter
    fun fromCar(name: Car): String {
        return Gson().toJson(name)
    }

    @TypeConverter
    fun toFlags(name: String): Flags {
        return Gson().fromJson(name, Flags::class.java)
    }

    @TypeConverter
    fun fromFlags(name: Flags): String {
        return Gson().toJson(name)
    }

    @TypeConverter
    fun toCoatOfArms(name: String): CoatOfArms {
        return Gson().fromJson(name, CoatOfArms::class.java)
    }

    @TypeConverter
    fun fromCoatOfArms(name: CoatOfArms): String {
        return Gson().toJson(name)
    }

    @TypeConverter
    fun toCapitalInfo(name: String): CapitalInfo {
        return Gson().fromJson(name, CapitalInfo::class.java)
    }

    @TypeConverter
    fun fromCapitalInfo(name: CapitalInfo): String {
        return Gson().toJson(name)
    }


    @TypeConverter
    fun toNativeName(name: String): NativeName {
        return Gson().fromJson(name, NativeName::class.java)
    }


    @TypeConverter
    fun fromNativeName(name: String): String {
        return Gson().toJson(name)
    }

}