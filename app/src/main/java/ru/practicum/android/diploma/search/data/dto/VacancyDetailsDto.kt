package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.regions.data.dto.AreaDto

data class VacancyDetailsDto(
    val id: String,
    val name: String,
    val description: String,
    val area: AreaDto,
    val salaryRange: SalaryDto? = null,
    val employer: EmployerDto? = null,
    val keySkills: List<String>,
    val experience: ExperienceDto? = null,
    val employmentForm: EmploymentFormDto? = null,
    val workFormat: WorkFormatDto? = null,
    val address: AddressDto? = null,
    val alternateUrl: String
)
