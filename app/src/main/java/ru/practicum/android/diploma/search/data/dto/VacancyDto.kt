package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.regions.data.dto.AreaDto

data class VacancyDto(
    val id: String,
    val name: String,
    val description: String,
    val area: AreaDto,
    val salary: SalaryDto,
    val employer: EmployerDto? = null,
    val keySkills: List<String>,
    val experience: ExperienceDto? = null,
)
