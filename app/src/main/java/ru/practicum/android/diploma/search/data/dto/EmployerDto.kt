package ru.practicum.android.diploma.search.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.search.data.dto.additional.LogoUrlsDto

data class EmployerDto(
    val id: String? = null,
    val name: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlsDto,
    val vacanciesUrl: String,
)
