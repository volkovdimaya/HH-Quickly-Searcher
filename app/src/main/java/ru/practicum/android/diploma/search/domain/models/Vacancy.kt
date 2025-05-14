package ru.practicum.android.diploma.search.domain.models

import ru.practicum.android.diploma.regions.data.dto.AreaDto
import ru.practicum.android.diploma.search.data.dto.EmployerDto
import ru.practicum.android.diploma.search.data.dto.ExperienceDto
import ru.practicum.android.diploma.search.data.dto.SalaryRangeDto

data class Vacancy(
    val id: String,
    val name: String,
    val description: String,
    val area: AreaDto,
    val salaryRange: SalaryRangeDto? = null,
    val employer: EmployerDto? = null,
    val keySkills: List<String>,
    val experience: ExperienceDto? = null,
)
