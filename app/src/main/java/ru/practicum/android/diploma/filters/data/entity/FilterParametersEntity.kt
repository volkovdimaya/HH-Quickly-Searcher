package ru.practicum.android.diploma.filters.data.entity

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "filter_parameters")
data class FilterParametersEntity(
    @SerializedName("country_id")
    val countryId: Int? = null,
    @SerializedName("region_id")
    val regionId: Int? = null,
    @SerializedName("industry_id")
    val industryId: Int? = null,
    @SerializedName("salary")
    val salary: Int? = null,
    @SerializedName("only_with_salary")
    val onlyWithSalary: Boolean = false,
    @SerializedName("country_name")
    val countryName: String? = null,
    @SerializedName("region_name")
    val regionName: String? = null,
    @SerializedName("industry_name")
    val industryName: String? = null,
    @SerializedName("salary_type")
    val salaryType: String? = null
)
