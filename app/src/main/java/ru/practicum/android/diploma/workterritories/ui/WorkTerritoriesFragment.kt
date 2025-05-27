package ru.practicum.android.diploma.workterritories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.workterritories.presentation.WorkTerritoriesViewModel

class WorkTerritoriesFragment : Fragment() {

    private val args: WorkTerritoriesFragmentArgs by navArgs()
    private val viewModel = WorkTerritoriesViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_work_territories, container, false)
    }
}
