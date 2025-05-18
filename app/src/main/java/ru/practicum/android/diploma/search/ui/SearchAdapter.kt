package ru.practicum.android.diploma.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.ui.ShortVacancyAdapter
import ru.practicum.android.diploma.common.ui.ShortVacancyViewHolder
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.databinding.ItemVacancyProgressbarBinding

class SearchAdapter : ShortVacancyAdapter() {

    override fun createViewHolder(parent: ViewGroup): ShortVacancyViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return SearchShortVacancyViewHolder(ItemVacancyBinding.inflate(layoutInspector, parent, false))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SHORT_VACANCY_VIEW_HOLDER -> {
                super.onCreateViewHolder(parent, viewType)
            }

            PROGRESS_BAR_VIEW_HOLDER -> {
                val layoutInspector = LayoutInflater.from(parent.context)
                ProgressBarViewHolder(
                    ItemVacancyProgressbarBinding.inflate(
                        layoutInspector,
                        parent,
                        false
                    )
                )
            }

            else -> {
                error(ERROR)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            in 0 until itemCount - 1 -> {
                SHORT_VACANCY_VIEW_HOLDER
            }

            itemCount - 1 -> {
                PROGRESS_BAR_VIEW_HOLDER
            }

            else -> {
                error(ERROR)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as SearchShortVacancyViewHolder).bind(item)
        when (position) {
            in 0 until itemCount - 1 -> (holder as SearchShortVacancyViewHolder).bind(item)
            itemCount - 1 -> holder as ProgressBarViewHolder
        }
    }

    override fun updateShortVacancyList(newList: List<VacancyShort>) {
        // update
    }

    companion object {
        const val SHORT_VACANCY_VIEW_HOLDER = 1
        const val PROGRESS_BAR_VIEW_HOLDER = 2
        const val ERROR = "Error exception SearchAdapter"
    }
}
