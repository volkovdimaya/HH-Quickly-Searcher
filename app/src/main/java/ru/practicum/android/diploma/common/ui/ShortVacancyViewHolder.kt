package ru.practicum.android.diploma.common.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.util.SizeFormatter
import ru.practicum.android.diploma.util.salaryFormatter

abstract class ShortVacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract val binding: ItemVacancyBinding

    open fun bind(vacancyShort: VacancyShort) {
        Glide.with(itemView.context)
            .load(vacancyShort.logoUrl).centerInside()
            .placeholder(R.drawable.ic_placeholder_32px)
            .centerCrop()
            .transform(RoundedCorners(SizeFormatter.dpToPx(ROUNDED_CORNER_RADIUS_DP, itemView.context)))
            .into(binding.logoImage)
        binding.nameVacancyWorkterritoryVacancy.text = buildString {
            append(vacancyShort.vacancyName)
            append(", ")
            append(vacancyShort.workTerritory.regionWork?.regionName ?: vacancyShort.workTerritory.country.countryName)

        }
        binding.employerName.text = vacancyShort.employer
        binding.salary.text = salaryFormatter(vacancyShort.salary, itemView.context)
    }

    companion object {
        const val ROUNDED_CORNER_RADIUS_DP = 12f
    }
}
