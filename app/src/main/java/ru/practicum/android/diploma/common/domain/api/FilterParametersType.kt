package ru.practicum.android.diploma.common.domain.api

sealed class FilterParametersType {
    class Country(val idCountry: Int) : FilterParametersType()
    class Region(val idRegion: Int) : FilterParametersType()
    class Industry(val idIndustry: Int) : FilterParametersType()
    class Salary(val salary: Int) : FilterParametersType()
    class OnlyWithSalary(val onlyWithSalary: Boolean) : FilterParametersType()

}
