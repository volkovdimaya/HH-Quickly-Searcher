package ru.practicum.android.diploma.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.common.data.dao.AreaDao
import ru.practicum.android.diploma.common.data.dao.IndustryDao
import ru.practicum.android.diploma.common.data.dao.VacancyDao
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity
import ru.practicum.android.diploma.filters.data.dao.FilterParametersDao
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity
import ru.practicum.android.diploma.industries.data.entity.IndustryEntity
import ru.practicum.android.diploma.vacancy.mapper.StringListConverter
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

@Database(
    version = 10,
    entities = [
        VacancyEntity::class,
        AreaEntity::class,
        IndustryEntity::class,
        FilterParametersEntity::class
    ]
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
    abstract fun areaDao(): AreaDao
    abstract fun industryDao(): IndustryDao
    abstract fun filterParametersDao(): FilterParametersDao
}
