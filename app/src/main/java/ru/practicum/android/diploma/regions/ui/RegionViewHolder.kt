package ru.practicum.android.diploma.regions.ui

import ru.practicum.android.diploma.common.ui.viewholder.BaseViewHolder
import ru.practicum.android.diploma.databinding.ItemRegionBinding
import ru.practicum.android.diploma.regions.domain.models.Region

class RegionViewHolder(override val binding: ItemRegionBinding) : BaseViewHolder<Region>(binding.root) {

    override fun bind(item: Region) {
        binding.regionName.text = item.regionName.trim()
        binding.regionName.requestLayout()
    }
}
