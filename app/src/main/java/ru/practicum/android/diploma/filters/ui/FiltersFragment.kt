package ru.practicum.android.diploma.filters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.filters.ui.models.FilterParametersUi

class FiltersFragment : Fragment() {

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    private val args: FiltersFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Вариант передачи данных.
         B граф навигации заложен navArgs для  @Parcelize-класса
         FilterParameters с defaultValue="@null"
         для использования при необходимости  */
        navigateToSearchFragment(FilterParametersUi())
    }

    private fun navigateToSearchFragment(filterParameters: FilterParametersUi) {
//        val action = FiltersFragmentDirections.actionFiltersFragmentToSearchFragment(filterParameters)
//        findNavController().navigate(action)
    }
}
