package ru.practicum.android.diploma.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.common.data.dao.AreaDao
import ru.practicum.android.diploma.common.data.dao.VacancyDao
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity
import ru.practicum.android.diploma.vacancy.mapper.StringListConverter
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

@Database(
    version = 3,
    entities = [
        VacancyEntity::class,
        AreaEntity::class,
    ]
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
    abstract fun areaDao(): AreaDao
}
