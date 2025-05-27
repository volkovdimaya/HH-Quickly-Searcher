package ru.practicum.android.diploma.countries.ui

import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.common.ui.viewholder.BaseViewHolder
import ru.practicum.android.diploma.databinding.ItemRegionBinding

class CountryViewHolder(override val binding: ItemRegionBinding) : BaseViewHolder<Country>(binding.root) {
    override fun bind(item: Country) {
        binding.regionName.text = item.countryName.trim()
    }
}
