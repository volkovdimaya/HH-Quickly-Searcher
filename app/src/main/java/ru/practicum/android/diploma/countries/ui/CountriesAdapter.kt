package ru.practicum.android.diploma.countries.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.common.ui.adapters.BaseAdapter
import ru.practicum.android.diploma.common.ui.viewholder.BaseViewHolder
import ru.practicum.android.diploma.databinding.ItemRegionBinding

class CountriesAdapter : BaseAdapter<Country>() {
    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<Country> {
        val layoutInspector = LayoutInflater.from(parent.context)
        return CountryViewHolder(ItemRegionBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as CountryViewHolder).bind(item)

        holder.itemView.setOnClickListener {
            setOnItemClickListener?.invoke(item)

        }
    }

    override fun updateList(newCountries: List<Country>) {
        list = newCountries
        notifyDataSetChanged()
    }

}
