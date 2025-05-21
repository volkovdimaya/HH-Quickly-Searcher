package ru.practicum.android.diploma.common.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity

@Dao
interface VacancyDao {

    // vacancy_table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancyEntity: VacancyEntity)

    @Delete(entity = VacancyEntity::class)
    suspend fun deleteVacancy(vacancyEntity: VacancyEntity)

    @Query("SELECT * FROM favorites")
    suspend fun getFavorites(): List<VacancyEntity>

    @Query("SELECT id FROM favorites")
    suspend fun getFavoritesId(): List<String>

    @Query("SELECT * FROM favorites WHERE id = :vacancyId")
    suspend fun getVacancyById(vacancyId: String): VacancyEntity

}
