package ru.practicum.android.diploma.regions.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.common.ui.adapters.BaseAdapter
import ru.practicum.android.diploma.common.ui.viewholder.BaseViewHolder
import ru.practicum.android.diploma.databinding.ItemRegionBinding
import ru.practicum.android.diploma.regions.domain.models.Region

class RegionsAdapter : BaseAdapter<Region>() {

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<Region> {
        val layoutInspector = LayoutInflater.from(parent.context)
        return RegionViewHolder(ItemRegionBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as RegionViewHolder).bind(item)
        holder.itemView.setOnClickListener {
            setOnItemClickListener?.invoke(item)
        }
    }

    override fun updateList(newList: List<Region>) {
        val oldRegionList = list

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldRegionList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldRegionList[oldItemPosition].regionId == newList[newItemPosition].regionId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldRegionList[oldItemPosition] == newList[newItemPosition]
            }
        })
        this.list = newList.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}
