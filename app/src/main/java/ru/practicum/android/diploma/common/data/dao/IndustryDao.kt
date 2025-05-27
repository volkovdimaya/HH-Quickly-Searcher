package ru.practicum.android.diploma.common.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.practicum.android.diploma.filters.data.dao.FilterParametersDao
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity
import ru.practicum.android.diploma.industries.data.entity.IndustryEntity

@Dao
interface IndustryDao : FilterParametersDao {

    // industries_table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIndustry(industryEntities: List<IndustryEntity>)

    @Delete(entity = IndustryEntity::class)
    suspend fun deleteIndustry(industryEntity: IndustryEntity)

    @Query("SELECT * FROM industries ")
    suspend fun getIndustries(): List<IndustryEntity>

    @Query("SELECT * FROM industries WHERE industryName LIKE '%' || :query || '%'")
    suspend fun searchIndustries(query: String): List<IndustryEntity>

    @Query("DELETE FROM industries")
    suspend fun clearTable()

    @Query("SELECT COUNT(*) FROM filter_parameters")
    suspend fun isFiltersEmpty(): Int

    @Query(
        "UPDATE filter_parameters SET industry_id = :id, industry_name = :industryName " +
            "WHERE filters_id = (SELECT filters_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateIndustry(id: String, industryName: String)

    @Transaction
    suspend fun updateIndustryParameter(parameters: FilterParametersEntity) {
        if (isFiltersEmpty() == 1) {
            updateIndustry(parameters.industryId ?: "", parameters.industryName ?: "")
        } else {
            saveFilters(parameters)
        }
    }
}
