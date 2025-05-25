package ru.practicum.android.diploma.filters.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FilterUpdateParametersDao {
    @Query(
        "UPDATE filter_parameters SET country_id = :id, country_name = :countryName " +
            "WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateCountry(id: Int?, countryName: String?)

    @Query(
        "UPDATE filter_parameters SET region_id = :id, region_name = :regionName " +
            "WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateRegion(id: Int?, regionName: String?)

    @Query(
        "UPDATE filter_parameters SET industry_id = :id, industry_name = :industryName " +
            "WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateIndustry(id: Int?, industryName: String?)

    @Query(
        "UPDATE filter_parameters SET salary = :salary, salary_type = :salaryType " +
            "WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateSalary(salary: Int?, salaryType: String? = null)

    @Query(
        "UPDATE filter_parameters SET only_with_salary = :onlyWithSalary " +
            "WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateOnlyWithSalary(onlyWithSalary: Boolean)
}
