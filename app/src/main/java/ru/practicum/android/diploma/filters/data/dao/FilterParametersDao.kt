package ru.practicum.android.diploma.filters.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity

@Dao
interface FilterParametersDao {

    @Query("SELECT * FROM filter_parameters LIMIT 1")
    suspend fun getParameters(): FilterParametersEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(parameters: FilterParametersEntity)

    @Query("UPDATE filter_parameters SET country_id = :id, country_name = :countryName WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)")
    suspend fun updateCountry(id: Int?, countryName: String?)

    @Query("UPDATE filter_parameters SET region_id = :id, region_name = :regionName WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)")
    suspend fun updateRegion(id: Int?, regionName: String?)

    @Query("UPDATE filter_parameters SET industry_id = :id, industry_name = :industryName WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)")
    suspend fun updateIndustry(id: Int?, industryName: String?)

    @Query("UPDATE filter_parameters SET salary = :salary, salary_type = :salaryType WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)")
    suspend fun updateSalary(salary: Int?, salaryType: String? = null)

    @Query("UPDATE filter_parameters SET only_with_salary = :onlyWithSalary WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)")
    suspend fun updateOnlyWithSalary(onlyWithSalary: Boolean)


    @Query("UPDATE filter_parameters SET region_id = NULL, region_name = NULL WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)")
    suspend fun clearRegion()

    @Query("UPDATE filter_parameters SET industry_id = NULL, industry_name = NULL WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)")
    suspend fun clearIndustry()

    @Query("UPDATE filter_parameters SET salary = NULL, salary_type = NULL WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)")
    suspend fun clearSalary()

    @Query("UPDATE filter_parameters SET only_with_salary = 0 WHERE country_id = (SELECT country_id FROM filter_parameters LIMIT 1)")
    suspend fun clearOnlyWithSalary()


}
