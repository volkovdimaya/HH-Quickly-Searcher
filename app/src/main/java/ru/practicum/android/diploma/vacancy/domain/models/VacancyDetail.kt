package ru.practicum.android.diploma.vacancy.domain.models

import ru.practicum.android.diploma.common.domain.models.Salary
import ru.practicum.android.diploma.common.domain.models.Vacancy
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.industries.domain.models.Industry

data class VacancyDetail(
    override val vacancyId: String,
    override val vacancyName: String,
    override val workTerritory: WorkTerritory,
    override val industry: Industry,
    override val salary: Salary,
    override val logoUrl: String,
    override val employer: String,
    val description: String,
    val address: String,
    val employment: EmploymentType,
    val keySkills: List<String>,
    val experience: String
) : Vacancy
