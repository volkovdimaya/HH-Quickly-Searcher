package ru.practicum.android.diploma.search.data.dto

data class VacancyDto(
    val id: String,
    val name: String,
    val description: String,
    val area: AreaDto,
    val salaryRange: SalaryRangeDto? = null,
    val employer: EmployerDto? = null,
    val keySkills: List<String>,
    val experience: ExperienceDto? = null,
)
