package ru.practicum.android.diploma.choosing_country.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.choosing_work_territory.ui.ChoosingWorkTerritoryFragmentArgs

class ChoosingCountryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_choosing_country, container, false)
    }
}
