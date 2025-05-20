package ru.practicum.android.diploma.common.domain.models

import ru.practicum.android.diploma.industries.domain.models.Industry

interface Vacancy {
    val vacancyId: Int
    val vacancyName: String
    val employer: String
    val workTerritory: WorkTerritory
    val industry: Industry
    val salary: Salary
    val logoUrl: String
}
