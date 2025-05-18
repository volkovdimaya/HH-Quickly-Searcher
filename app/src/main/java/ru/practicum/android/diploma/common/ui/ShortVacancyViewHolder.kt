package ru.practicum.android.diploma.common.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.util.DecimalNumberFormatter
import ru.practicum.android.diploma.util.SizeFormatter

abstract class ShortVacancyViewHolder(private val binding: ItemVacancyBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(vacancyShort: VacancyShort) {
        Glide.with(itemView.context)
            .load(vacancyShort.logoUrl).centerInside()
            .placeholder(R.drawable.ic_placeholder_32px)
            .centerCrop()
            .transform(RoundedCorners(SizeFormatter.dpToPx(2f, itemView.context)))
            .into(binding.logoImage)
        binding.nameVacancyWorkterritoryVacancy.text = buildString {
            append(vacancyShort.vacancyName)
            append(", ")
            append(vacancyShort.workTerritory.regionWork?.regionName ?: vacancyShort.workTerritory.country.countryName)

        }
        binding.employerName.text = vacancyShort.employer
        binding.salary.text = buildString {
            vacancyShort.salary.salaryFrom?.let {
                this.append("от ")
                this.append(DecimalNumberFormatter.formatNumber(it))
            }
            vacancyShort.salary.salaryTo?.let {
                this.append(" до ")
                this.append(DecimalNumberFormatter.formatNumber(it))
            }

            if (this.isNotEmpty()) {
                this.append(" ")
                this.append(vacancyShort.salary.salaryCurrency.symbol)
            } else {
                this.append(itemView.context.getString(R.string.salary_is_not_said))
            }
        }
    }
}
