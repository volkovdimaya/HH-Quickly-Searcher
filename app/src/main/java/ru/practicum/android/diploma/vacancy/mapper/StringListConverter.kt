package ru.practicum.android.diploma.vacancy.mapper

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class StringListConverter(private val gson: Gson) {

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(data: String?): List<String>? {
        if (data.isNullOrEmpty()) return null
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, type)
    }
}
