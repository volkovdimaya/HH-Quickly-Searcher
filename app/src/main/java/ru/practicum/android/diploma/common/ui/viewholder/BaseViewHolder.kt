package ru.practicum.android.diploma.common.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract val binding: ViewBinding

    abstract fun bind(item: T)
}
