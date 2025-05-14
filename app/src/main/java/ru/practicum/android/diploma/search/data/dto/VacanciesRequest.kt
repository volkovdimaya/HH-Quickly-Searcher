package ru.practicum.android.diploma.search.data.dto

data class VacanciesRequest(
    val text: String,
    val area: String? = null,
    val professionalRole: String? = null,
    val industry: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
    val page: Int = 0,
    val perPage: Int = 20
) {
    fun toMap(): Map<String, String> = buildMap {
        put("text", text)
        area?.let { put("area", it) }
        professionalRole?.let { put("professional_role", it) }
        industry?.let { put("industry", it) }
        salary?.let { put("salary", it.toString()) }
        if (onlyWithSalary) put("only_with_salary", "true")
        put("page", page.toString())
        put("per_page", perPage.toString())
    }
}
