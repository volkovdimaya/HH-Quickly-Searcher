package ru.practicum.android.diploma.countries.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.common.ui.viewholder.BaseViewHolder
import ru.practicum.android.diploma.databinding.ItemRegionBinding

class AdapterCountries : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var countries: List<Country> = emptyList()
    var onItemClickListener: ((Country) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemRegionBinding = ItemRegionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CountryViewHolder) {
            holder.bind(countries[position])
            holder.itemView.setOnClickListener {
                onItemClickListener?.invoke(countries[position])
            }
        }
    }
    fun updateCountries(newCountries: List<Country>) {
        countries = newCountries.sortedBy { it.countryName }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = countries.size


    inner class CountryViewHolder( override val binding: ItemRegionBinding) : BaseViewHolder<Country>(binding.root) {
        override fun bind(item: Country) {
            binding.regionName.text = item.countryName
        }
    }


}
