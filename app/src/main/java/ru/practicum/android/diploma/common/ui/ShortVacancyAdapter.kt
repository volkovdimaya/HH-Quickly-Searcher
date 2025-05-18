package ru.practicum.android.diploma.common.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.common.domain.models.VacancyShort

abstract class ShortVacancyAdapter : RecyclerView.Adapter<ShortVacancyViewHolder>() {

    var list: List<VacancyShort> = emptyList()
    var setOnItemClickListener: ((VacancyShort) -> Unit)? = null

    abstract fun createViewHolder(parent: ViewGroup): ShortVacancyViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortVacancyViewHolder {
        return createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ShortVacancyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            setOnItemClickListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    abstract fun updateShortVacancyList(newList: List<VacancyShort>)
}
