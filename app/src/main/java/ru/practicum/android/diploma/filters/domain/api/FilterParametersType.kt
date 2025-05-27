package ru.practicum.android.diploma.filters.domain.api

sealed class FilterParametersType {
    class Country(val countryId: Int? = null, val countryName: String? = null) : FilterParametersType()
    class Region(val regionId: Int? = null, val regionName: String? = null) : FilterParametersType()
    class Industry(val industryId: String, val industryName: String) : FilterParametersType()
    class Salary(val salary: Int) : FilterParametersType()
    class OnlyWithSalary(val onlyWithSalary: Boolean) : FilterParametersType()
}
