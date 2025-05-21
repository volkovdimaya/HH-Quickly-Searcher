package ru.practicum.android.diploma.common.domain.models

data class VacancyShort(
    override val vacancyId: String,
    override val vacancyName: String,
    override val workTerritory: String,
    override val salary: Salary,
    override val logoUrl: String,
    override val employer: String
) : Vacancy
