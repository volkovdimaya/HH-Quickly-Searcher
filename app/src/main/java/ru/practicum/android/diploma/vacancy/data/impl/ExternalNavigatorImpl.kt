package ru.practicum.android.diploma.vacancy.data.impl

import android.content.Context
import android.content.Intent
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.vacancy.domain.api.ExternalNavigator

class ExternalNavigatorImpl(
    val context: Context
) : ExternalNavigator {

    override fun shareString(str: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, str)
        shareIntent.setType("text/plain")
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(
            Intent.createChooser(
                shareIntent,
                context.getString(R.string.sharing_title)
            )
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}
