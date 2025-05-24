package ru.practicum.android.diploma.industries.ui

import ru.practicum.android.diploma.common.ui.viewholder.BaseViewHolder
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.industries.domain.models.Industry

class IndustryViewHolder(override val binding: ItemIndustryBinding) : BaseViewHolder<Industry>(binding.root) {

    override fun bind(item: Industry) {
        binding.industryName.text = item.industryName.trim()
        binding.industryName.requestLayout()
    }
}
