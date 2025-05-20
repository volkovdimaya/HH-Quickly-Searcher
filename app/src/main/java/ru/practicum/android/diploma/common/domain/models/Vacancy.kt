package ru.practicum.android.diploma.common.domain.models

interface Vacancy {
    val vacancyId: String
    val vacancyName: String
    val employer: String
    val workTerritory: String
    val salary: Salary
    val logoUrl: String
}
