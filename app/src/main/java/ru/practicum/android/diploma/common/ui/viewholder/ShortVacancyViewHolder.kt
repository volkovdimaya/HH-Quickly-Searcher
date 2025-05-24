package ru.practicum.android.diploma.common.ui.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.util.SizeFormatter
import ru.practicum.android.diploma.util.salaryFormatter

abstract class ShortVacancyViewHolder(itemView: View) : BaseViewHolder<VacancyShort>(itemView) {

    abstract override val binding: ItemVacancyBinding

    override fun bind(item: VacancyShort) {
        Glide.with(itemView.context)
            .load(item.logoUrl)
            .error(R.drawable.ic_placeholder_48px)
            .placeholder(R.drawable.ic_placeholder_48px)
            .centerCrop()
            .transform(RoundedCorners(SizeFormatter.dpToPx(ROUNDED_CORNER_RADIUS_DP, itemView.context)))
            .into(binding.logoImage)
        binding.nameVacancyWorkterritoryVacancy.text = buildString {
            append(item.vacancyName)
            append(", ")
            append(item.workTerritory)

        }
        binding.employerName.text = item.employer
        binding.salary.text = salaryFormatter(item.salary, itemView.context)
    }

    companion object {
        const val ROUNDED_CORNER_RADIUS_DP = 12f
    }
}
