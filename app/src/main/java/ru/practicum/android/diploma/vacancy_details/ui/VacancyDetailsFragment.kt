package ru.practicum.android.diploma.vacancy_details.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.ui.SearchFragmentArgs

class VacancyDetailsFragment : Fragment() {

    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vacancy_details, container, false)
    }
}
