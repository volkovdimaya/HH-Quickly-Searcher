package ru.practicum.android.diploma.filters.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity

@Dao
interface FilterParametersDao {

    @Insert(entity = FilterParametersEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun createFilters(filterParametersEntity: FilterParametersEntity)

    @Delete(entity = FilterParametersEntity::class)
    suspend fun deleteFilters(filterParametersEntity: FilterParametersEntity)

    @Update(entity = FilterParametersEntity::class)
    suspend fun updateFilters(filterParametersEntity: FilterParametersEntity)

    @Query("SELECT * FROM filter_parameters WHERE filters_id = :filterId")
    suspend fun getFilters(filterId: String): List<FilterParametersEntity>
}
