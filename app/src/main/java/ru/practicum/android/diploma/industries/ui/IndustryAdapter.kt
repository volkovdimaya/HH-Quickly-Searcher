package ru.practicum.android.diploma.industries.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.common.ui.adapters.BaseAdapter
import ru.practicum.android.diploma.common.ui.viewholder.BaseViewHolder
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.industries.domain.models.Industry

class IndustryAdapter : BaseAdapter<Industry>() {

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<Industry> {
        val layoutInspector = LayoutInflater.from(parent.context)
        return IndustryViewHolder(ItemIndustryBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as IndustryViewHolder).bind(item)
        holder.itemView.setOnClickListener {
            setOnItemClickListener?.invoke(item)
        }
    }

    override fun updateList(newList: List<Industry>) {
        val oldIndustryList = list
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldIndustryList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldIndustryList[oldItemPosition].industryId == newList[newItemPosition].industryId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldIndustryList[oldItemPosition]
                val newItem = newList[newItemPosition]

                return oldItem.industryId == newItem.industryId &&
                    oldItem.industryName == newItem.industryName &&
                    oldItem.select.isSelected == newItem.select.isSelected
            }
        })
        this.list = newList.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}
