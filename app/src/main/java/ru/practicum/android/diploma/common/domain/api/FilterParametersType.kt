package ru.practicum.android.diploma.common.domain.api

sealed class FilterParametersType {
    class Country(val idCountry: Int, val name : String) : FilterParametersType()
    class Region(val idRegion: Int, val name : String) : FilterParametersType()
    class Industry(val idIndustry: Int, val name : String) : FilterParametersType()
    class Salary(val salary: Int, val type : String) : FilterParametersType()
    class OnlyWithSalary(val onlyWithSalary: Boolean) : FilterParametersType()

}
