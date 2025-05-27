package ru.practicum.android.diploma.common.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.practicum.android.diploma.filters.data.dao.FilterParametersCreateDao
import ru.practicum.android.diploma.filters.data.dao.FilterUpdateParametersDao
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity
import ru.practicum.android.diploma.industries.data.entity.IndustryEntity

@Dao
interface IndustryDao : FilterUpdateParametersDao, FilterParametersCreateDao {

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


    @Transaction
    suspend fun updateIndustryParameter(parameters: FilterParametersEntity) {
        if (isFiltersEmpty() == 1) {
            updateIndustry(parameters.countryId.toString() ?: "", parameters.countryName ?: "")
        } else {
            insert(parameters)
        }
    }

    @Transaction
    suspend fun updateCountryParameter(parameters: FilterParametersEntity) {
        if (isFiltersEmpty() == 1) {
            updateCountry(parameters.countryId, parameters.countryName ?: "")
        } else {
            insert(parameters)
        }
    }

    @Transaction
    suspend fun updateRegionParameter(parameters: FilterParametersEntity) {
        if (isFiltersEmpty() == 1) {
            updateRegion(parameters.regionId, parameters.regionName ?: "")
        } else {
            insert(parameters)
        }
    }


}
