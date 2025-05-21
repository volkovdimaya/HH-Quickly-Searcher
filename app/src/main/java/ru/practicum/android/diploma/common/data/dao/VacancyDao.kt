package ru.practicum.android.diploma.common.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.practicum.android.diploma.common.data.models.VacancyWithWorkTerritory
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity

@Dao
interface VacancyDao {

    // vacancy_table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancyEntity: VacancyEntity)

    @Delete(entity = VacancyEntity::class)
    suspend fun deleteVacancy(vacancyEntity: VacancyEntity)

    @Query("SELECT COUNT(*) FROM favorites WHERE workTerritoryId = :workTerritoryId")
    suspend fun getCountRegions(workTerritoryId: String): Int

    @Query("SELECT COUNT(*) FROM favorites WHERE workTerritoryCountryId = :workTerritoryCountryId")
    suspend fun getCountCountries(workTerritoryCountryId: String): Int

    @Transaction
    @Query("SELECT * FROM favorites")
    suspend fun getFavorites(): List<VacancyWithWorkTerritory>

}
