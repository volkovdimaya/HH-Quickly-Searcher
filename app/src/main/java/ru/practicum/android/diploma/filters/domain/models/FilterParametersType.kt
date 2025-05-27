package ru.practicum.android.diploma.filters.domain.models

sealed class FilterParametersType {
    class Country(val countryId: Int? = null, val countryName: String? = null) : FilterParametersType()
    class Region(val regionId: Int? = null, val regionName: String? = null) : FilterParametersType()
    class Industry(val industryId: String? = null, val industryName: String? = null) : FilterParametersType()
    class Salary(val salary: Int? = null) : FilterParametersType()
    class OnlyWithSalary(val onlyWithSalary: Boolean = false) : FilterParametersType()
}
