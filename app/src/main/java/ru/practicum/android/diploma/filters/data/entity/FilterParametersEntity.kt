package ru.practicum.android.diploma.filters.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filter_parameters")
data class FilterParametersEntity(
    @PrimaryKey
    @ColumnInfo("filters_id")
    val filtersId: String,
    @ColumnInfo("country_id")
    val countryId: Int = 0,
    @ColumnInfo("region_id")
    val regionId: Int? = null,
    @ColumnInfo("industry_id")
    val industryId: Int? = null,
    @ColumnInfo("salary")
    val salary: Int? = null,
    @ColumnInfo("only_with_salary")
    val onlyWithSalary: Boolean = false,
    @ColumnInfo("country_name")
    val countryName: String? = null,
    @ColumnInfo("region_name")
    val regionName: String? = null,
    @ColumnInfo("industry_name")
    val industryName: String? = null
)
