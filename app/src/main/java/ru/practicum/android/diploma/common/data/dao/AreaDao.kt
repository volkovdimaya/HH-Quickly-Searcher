package ru.practicum.android.diploma.common.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.data.dao.FilterParametersDao
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

@Dao
interface AreaDao : FilterParametersDao {

    // areas_table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAreas(areaEntities: List<AreaEntity>)

    @Delete(entity = AreaEntity::class)
    suspend fun deleteArea(areaEntity: AreaEntity)

    @Query("SELECT * FROM areas ")
    suspend fun getAreas(): List<AreaEntity>

    @Query("SELECT * FROM areas WHERE areaName LIKE '%' || :query || '%'")
    suspend fun searchAreas(query: String): List<AreaEntity>

    @Query("DELETE FROM areas")
    suspend fun clearTable()

    @Query("SELECT * FROM areas WHERE areaId = :countryId LIMIT 1")
    suspend fun getAreaById(countryId: String): AreaEntity?

    @Query("SELECT COUNT(*) FROM filter_parameters")
    suspend fun isFiltersEmpty(): Int

    @Query("SELECT * FROM filter_parameters LIMIT 1")
    fun observeFilterParameters(): Flow<FilterParametersEntity?>

    @Query(
        "UPDATE filter_parameters SET region_id = :id, region_name = :regionName " +
            "WHERE filters_id = (SELECT filters_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateRegion(id: String, regionName: String)

    @Transaction
    suspend fun updateAreaParameter(parameters: FilterParametersEntity) {
        if (isFiltersEmpty() == 1) {
            updateRegion(parameters.regionId.toString() ?: "", parameters.regionName ?: "")
        } else {
            saveFilters(parameters)
        }
    }

    @Query("SELECT * FROM filter_parameters LIMIT 1")
    suspend fun getParameters(): FilterParametersEntity
}
