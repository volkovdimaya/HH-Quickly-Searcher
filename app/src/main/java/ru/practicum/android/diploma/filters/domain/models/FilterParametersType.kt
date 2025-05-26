package ru.practicum.android.diploma.filters.domain.models

sealed class FilterParametersType {
    class Country(val countryId: Int, val countryName: String) : FilterParametersType()
    class Region(val regionId: Int, val regionName: String) : FilterParametersType()
    class Industry(val industryId: Int, val industryName: String) : FilterParametersType()
    class Salary(val salary: Int) : FilterParametersType()
    class OnlyWithSalary(val onlyWithSalary: Boolean) : FilterParametersType()
}
