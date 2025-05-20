package ru.practicum.android.diploma.common.domain.models

import ru.practicum.android.diploma.industries.domain.models.Industry

data class VacancyShort(
    override val vacancyId: String,
    override val vacancyName: String,
    override val workTerritory: WorkTerritory,
    override val salary: Salary,
    override val logoUrl: String,
    override val employer: String
) : Vacancy
