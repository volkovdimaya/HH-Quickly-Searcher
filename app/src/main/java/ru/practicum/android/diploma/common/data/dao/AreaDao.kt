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
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

@Dao
interface AreaDao : FilterUpdateParametersDao, FilterParametersCreateDao {

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

    @Transaction
    suspend fun updateAreaParameter(parameters: FilterParametersEntity) {
        if (isFiltersEmpty() == 1) {
            updateRegion(parameters.regionId ?: "", parameters.regionName ?: "")
        } else {
            insert(parameters)
        }
    }
}
