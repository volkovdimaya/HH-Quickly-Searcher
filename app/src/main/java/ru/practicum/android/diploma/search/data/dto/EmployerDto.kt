package ru.practicum.android.diploma.search.data.dto

data class EmployerDto(
    val id: String? = null,
    val name: String,
    val logoUrls: List<String>,
    val vacanciesUrl: String,
)
