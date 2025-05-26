package ru.practicum.android.diploma.filters.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity

@Dao
interface FilterParametersCreateDao {
    @Query("SELECT * FROM filter_parameters LIMIT 1")
    suspend fun getParameters(): FilterParametersEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(parameters: FilterParametersEntity)
}
