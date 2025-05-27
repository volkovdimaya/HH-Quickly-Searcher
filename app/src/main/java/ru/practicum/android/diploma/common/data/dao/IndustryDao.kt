package ru.practicum.android.diploma.common.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.industries.data.entity.IndustryEntity

@Dao
interface IndustryDao {

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
}
