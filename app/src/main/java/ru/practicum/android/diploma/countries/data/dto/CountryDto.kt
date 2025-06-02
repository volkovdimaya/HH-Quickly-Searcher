package ru.practicum.android.diploma.countries.data.dto

import com.google.gson.annotations.SerializedName

data class CountryDto(
    val id: Int,
    val name: String,
    @SerializedName("parent_id")
    val parentId: String? = null,
)
