package ru.practicum.android.diploma.common.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.common.domain.models.VacancyShort

abstract class ShortVacancyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: List<VacancyShort> = emptyList()
    var setOnItemClickListener: ((VacancyShort) -> Unit)? = null

    abstract fun createViewHolder(parent: ViewGroup): ShortVacancyViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as ShortVacancyViewHolder).bind(item)
        holder.itemView.setOnClickListener {
            setOnItemClickListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    abstract fun updateShortVacancyList(newList: List<VacancyShort>)
}
