package ru.practicum.android.diploma.vacancy.domain.models

import ru.practicum.android.diploma.common.domain.models.Salary
import ru.practicum.android.diploma.common.domain.models.Vacancy

data class VacancyDetail(
    override val vacancyId: String,
    override val vacancyName: String,
    override val workTerritory: String,
    override val salary: Salary,
    override val logoUrl: String,
    override val employer: String,
    val description: String,
    val address: String,
    val employment: EmploymentType,
    val keySkills: List<String>,
    val experience: String,
    val vacancyUrl: String
) : Vacancy
