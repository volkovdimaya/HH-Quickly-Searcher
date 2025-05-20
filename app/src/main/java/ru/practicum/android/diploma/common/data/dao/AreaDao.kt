package ru.practicum.android.diploma.common.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

@Dao
interface AreaDao {

    // area_table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArea(areaEntity: AreaEntity)

    @Delete(entity = AreaEntity::class)
    suspend fun deleteArea(areaEntity: AreaEntity)

    @Query("SELECT * FROM areas WHERE areaId = :areaId")
    suspend fun getArea(areaId: String): AreaEntity
}
