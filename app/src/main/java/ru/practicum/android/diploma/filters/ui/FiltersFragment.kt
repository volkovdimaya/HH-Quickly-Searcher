package ru.practicum.android.diploma.filters.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.practicum.android.diploma.common.ui.models.FilterParameters
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding

class FiltersFragment : Fragment() {

    private lateinit var binding : FragmentFiltersBinding

    private val args: FiltersFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* B граф навигации заложен navArgs для  @Parcelize класса
         FilterParameters с defaultValue="@null"
         для использования при необходимости  */
        navigateToSearchFragment(FilterParameters())
    }


    private fun navigateToSearchFragment(filterParameters: FilterParameters) {
        val action = FiltersFragmentDirections.actionFiltersFragmentToSearchFragment(filterParameters)
        findNavController().navigate(action)
    }
}
