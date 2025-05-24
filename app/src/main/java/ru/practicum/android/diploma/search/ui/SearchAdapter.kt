package ru.practicum.android.diploma.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.ui.ShortVacancyAdapter
import ru.practicum.android.diploma.common.ui.ShortVacancyViewHolder
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.databinding.ItemVacancyProgressbarBinding

class SearchAdapter : ShortVacancyAdapter() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    var isLoadingMore = false
        private set

    private var lastVisiblePosition = 0
    private var recyclerView: RecyclerView? = null

    override fun getItemViewType(position: Int): Int {
        return if (isLoadingMore && position == list.size) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (recyclerView == null && parent is RecyclerView) {
            recyclerView = parent
        }

        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_LOADING -> {
                val binding = ItemVacancyProgressbarBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding)
            }
            else -> {
                createViewHolder(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ShortVacancyViewHolder && position < list.size) {
            holder.bind(list[position])
            holder.itemView.setOnClickListener {
                setOnItemClickListener?.invoke(list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return if (isLoadingMore) list.size + 1 else list.size
    }

    override fun createViewHolder(parent: ViewGroup): ShortVacancyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchShortVacancyViewHolder(ItemVacancyBinding.inflate(layoutInflater, parent, false))
    }

    override fun updateShortVacancyList(newList: List<VacancyShort>) {
        saveScrollPosition()

        val oldSearchList = list
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldSearchList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldSearchList[oldItemPosition].vacancyId == newList[newItemPosition].vacancyId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldSearchList[oldItemPosition] == newList[newItemPosition]
            }
        })

        this.list = newList.toList()
        diffResult.dispatchUpdatesTo(this)

        restoreScrollPosition()
    }

    fun setLoadingMore(loading: Boolean) {
        if (this.isLoadingMore != loading) {
            saveScrollPosition()

            this.isLoadingMore = loading
            if (loading) {
                notifyItemInserted(list.size)
            } else {
                notifyItemRemoved(list.size)
            }

            restoreScrollPosition()
        }
    }

    fun addItems(newItems: List<VacancyShort>) {
        if (newItems.isEmpty()) return
        saveScrollPosition()
        val startPos = list.size
        val newList = list.toMutableList()
        newList.addAll(newItems)
        list = newList
        notifyItemRangeInserted(startPos, newItems.size)
        restoreScrollPosition()
    }

    private fun saveScrollPosition() {
        val layoutManager = recyclerView?.layoutManager as? LinearLayoutManager ?: return
        lastVisiblePosition = layoutManager.findFirstVisibleItemPosition()
    }

    private fun restoreScrollPosition() {
        if (lastVisiblePosition < 0) return

        val layoutManager = recyclerView?.layoutManager as? LinearLayoutManager ?: return
        recyclerView?.post {
            layoutManager.scrollToPosition(lastVisiblePosition)
        }
    }
}
