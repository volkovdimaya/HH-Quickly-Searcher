package ru.practicum.android.diploma.countries.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.search.data.dto.Response

data class AreaNetworkDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("parent_id")
    val parentId: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("areas")
    val areas: List<AreaNetworkDto>
)
