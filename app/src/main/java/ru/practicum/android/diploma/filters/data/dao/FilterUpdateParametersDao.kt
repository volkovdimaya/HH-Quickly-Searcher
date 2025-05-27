package ru.practicum.android.diploma.filters.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FilterUpdateParametersDao {

    @Query(
        "UPDATE filter_parameters SET country_id = :id, country_name = :countryName " +
            "WHERE id = (SELECT id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateCountry(id: Int?, countryName: String?)

    @Query(
        "UPDATE filter_parameters SET region_id = :id, region_name = :regionName " +
            "WHERE id = (SELECT id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateRegion(id: String, regionName: String)

    @Query(
        "UPDATE filter_parameters SET industry_id = :id, industry_name = :industryName " +
            "WHERE id = (SELECT id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateIndustry(id: String, industryName: String)

    @Query(
        "UPDATE filter_parameters SET salary = :salary, salary_type = :salaryType " +
            "WHERE id = (SELECT id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateSalary(salary: Int?, salaryType: String? = null)

    @Query(
        "UPDATE filter_parameters SET only_with_salary = :onlyWithSalary " +
            "WHERE id = (SELECT id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateOnlyWithSalary(onlyWithSalary: Boolean)

    @Query("SELECT COUNT(*) FROM filter_parameters")
    suspend fun isFiltersEmpty(): Int

}
