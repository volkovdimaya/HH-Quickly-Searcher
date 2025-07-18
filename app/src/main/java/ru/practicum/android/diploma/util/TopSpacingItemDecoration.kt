package ru.practicum.android.diploma.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TopSpacingItemDecoration(
    private val topSpacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == 0) {
            outRect.top = topSpacing
        }
    }
}

// val spacingInPixels = resources.getDimensionPixelSize(R.dimen.recycler_top_spacing) //38dp
// recyclerViewHistoryTrack.addItemDecoration(TopSpacingItemDecoration(spacingInPixels))
