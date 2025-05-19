package ru.practicum.android.diploma.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.common.data.dao.AreaDao
import ru.practicum.android.diploma.common.data.dao.VacancyDao
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity
import ru.practicum.android.diploma.vacancy.data.entity.KeySkillEntity
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

@Database(
    version = 2,
    entities = [
        VacancyEntity::class,
        AreaEntity::class,
        KeySkillEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
    abstract fun areaDao(): AreaDao
}
