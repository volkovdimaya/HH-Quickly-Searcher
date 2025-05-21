package ru.practicum.android.diploma.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.ui.ShortVacancyAdapter
import ru.practicum.android.diploma.common.ui.ShortVacancyViewHolder
import ru.practicum.android.diploma.databinding.ItemVacancyBinding

class SearchAdapter : ShortVacancyAdapter() {

    override fun createViewHolder(parent: ViewGroup): ShortVacancyViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return SearchShortVacancyViewHolder(ItemVacancyBinding.inflate(layoutInspector, parent, false))
    }

    override fun updateShortVacancyList(newList: List<VacancyShort>) {
        val oldSearchList = list
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldSearchList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldSearchList[oldItemPosition].vacancyId == newList[newItemPosition].vacancyId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldSearchList[oldItemPosition] == newList[newItemPosition]
            }
        })

        this.list = newList.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}
