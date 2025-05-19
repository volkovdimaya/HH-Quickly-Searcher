package ru.practicum.android.diploma.vacancy.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity

@Entity(
    tableName = "key_skill",
    foreignKeys = [
        ForeignKey(
            entity = VacancyEntity::class,
            parentColumns = ["id"],
            childColumns = ["vacancyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["vacancyId"])
    ]
)
data class KeySkillEntity(
    @PrimaryKey(autoGenerate = true)
    val keySkillId: Int = 0,
    val vacancyId: String,
    val keySkill: String,
)
