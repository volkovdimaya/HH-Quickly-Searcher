package ru.practicum.android.diploma.filters.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity

@Dao
interface FilterParametersDao {

    @Insert(entity = FilterParametersEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilters(filterParametersEntity: FilterParametersEntity)

    @Delete(entity = FilterParametersEntity::class)
    suspend fun deleteFilters(filterParametersEntity: FilterParametersEntity)

    @Query("SELECT * FROM filter_parameters WHERE filters_id = :filterId")
    suspend fun getFilters(filterId: String): List<FilterParametersEntity>

    @Query("SELECT * FROM filter_parameters WHERE filters_id = :filterId")
    fun getFiltersObserver(filterId: String): Flow<FilterParametersEntity?>

    @Query(
        "UPDATE filter_parameters SET country_id = :id, industry_name = :industryName " +
            "WHERE filters_id = (SELECT filters_id FROM filter_parameters LIMIT 1)"
    )
    suspend fun updateCountry(id: Nothing?, industryName: Nothing?)
}
