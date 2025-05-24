package ru.practicum.android.diploma.common.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.common.ui.viewholder.BaseViewHolder

abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: List<T> = emptyList()
    var setOnItemClickListener: ((T) -> Unit)? = null

    abstract fun createViewHolder(parent: ViewGroup): BaseViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        @Suppress("UNCHECKED_CAST")
        (holder as BaseViewHolder<T>).bind(item)
        holder.itemView.setOnClickListener {
            setOnItemClickListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    abstract fun updateList(newList: List<T>)
}
