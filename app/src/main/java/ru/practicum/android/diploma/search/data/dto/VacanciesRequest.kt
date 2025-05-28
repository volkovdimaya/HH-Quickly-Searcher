package ru.practicum.android.diploma.search.data.dto

data class VacanciesRequest(
    val text: String,
    val area: String? = null,
    val industry: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
    val page: Int = 0,
    val perPage: Int = 20
)

fun VacanciesRequest.toMap(): Map<String, String> = buildMap {
    put("text", text)
    if (area.isNullOrBlank() && area != "0") { put("area", area!!) }
    industry?.let { put("industry", it) }
    salary?.let { put("salary", it.toString()) }
    put("only_with_salary", onlyWithSalary.toString())
    put("page", page.toString())
    put("per_page", perPage.toString())
}
