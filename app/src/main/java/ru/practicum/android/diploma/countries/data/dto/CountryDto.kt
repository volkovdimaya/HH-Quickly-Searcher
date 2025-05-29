package ru.practicum.android.diploma.countries.data.dto

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
