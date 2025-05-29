package ru.practicum.android.diploma.common.ui.adapters

import android.view.ViewGroup
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.ui.viewholder.ShortVacancyViewHolder

abstract class ShortVacancyAdapter : BaseAdapter<VacancyShort>() {

    abstract override fun createViewHolder(parent: ViewGroup): ShortVacancyViewHolder

    override fun updateList(newList: List<VacancyShort>) {
        updateShortVacancyList(newList)
    }
    abstract fun updateShortVacancyList(newList: List<VacancyShort>)
}
