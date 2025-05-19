package ru.practicum.android.diploma.util

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.progressindicator.CircularProgressIndicator
import ru.practicum.android.diploma.R

class CustomCircularProgressIndicator(context: Context) {

    val progressIndicator = CircularProgressIndicator(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }
        isIndeterminate = true
        indicatorSize = SizeFormatter.dpToPx(INDICATOR_SIZE_DP, context)
        progress = PROGRESS
        setIndicatorColor(ContextCompat.getColor(context, R.color.blue))
    }

    companion object {
        private const val INDICATOR_SIZE_DP = 36f
        private const val PROGRESS = 75
    }
}
