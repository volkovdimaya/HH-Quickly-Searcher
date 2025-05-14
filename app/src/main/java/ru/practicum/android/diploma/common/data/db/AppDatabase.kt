package ru.practicum.android.diploma.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity

@Database(version = 1, entities = [VacancyEntity::class])
abstract class AppDatabase : RoomDatabase()
