package ru.practicum.android.diploma.search.ui

import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.ui.ShortVacancyViewHolder
import ru.practicum.android.diploma.databinding.ItemVacancyBinding

class SearchShortVacancyViewHolder(override val binding: ItemVacancyBinding) : ShortVacancyViewHolder(binding.root) {

    override fun bind(vacancyShort: VacancyShort) {
        super.bind(vacancyShort)
    }
}
