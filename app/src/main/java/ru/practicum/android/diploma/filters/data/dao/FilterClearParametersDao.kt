package ru.practicum.android.diploma.filters.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FilterClearParametersDao {
    @Query(
        "UPDATE filter_parameters SET country_id = NULL, country_name = NULL " +
            "WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun clearCountry()

    @Query(
        "UPDATE filter_parameters SET region_id = NULL, region_name = NULL " +
            "WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun clearRegion()

    @Query(
        "UPDATE filter_parameters SET industry_id = NULL, industry_name = NULL " +
            "WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun clearIndustry()

    @Query(
        "UPDATE filter_parameters SET salary = NULL, salary_type = NULL " +
            "WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun clearSalary()

    @Query(
        "UPDATE filter_parameters SET only_with_salary = 0 " +
            "WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun clearOnlyWithSalary()
}
