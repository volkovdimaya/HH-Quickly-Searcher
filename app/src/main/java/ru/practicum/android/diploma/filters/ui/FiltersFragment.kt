package ru.practicum.android.diploma.filters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.filters.domain.models.FilterParametersType
import ru.practicum.android.diploma.filters.presentation.api.FiltersViewModel

class FiltersFragment : Fragment() {

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FiltersViewModel>()

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

        setAllClickListeners()
        binding.expectedSalaryEditText.setOnFocusChangeListener { _, focused ->
            binding.expectedSalaryLayout.hintTextColor = if (focused) {
                requireContext().getColorStateList(R.color.blue)
            } else if (binding.expectedSalaryEditText.text.isNullOrEmpty()) {
                requireContext().getColorStateList(R.color.colorOnSurfaceVariant)
            } else {
                requireContext().getColorStateList(R.color.black)
            }
            binding.expectedSalaryLayout.isEndIconVisible = focused
                && !binding.expectedSalaryEditText.text.isNullOrEmpty()
        }

        viewModel.getFilterParametersState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun setAllClickListeners() {
        binding.industry.setOnClickListener {
            findNavController().navigate(R.id.industriesFragment)
        }
        binding.industrySelected.setOnClickListener{
            findNavController().navigate(R.id.industriesFragment)
        }
        binding.industryDelete.setOnClickListener {
            viewModel.deleteFilterParameter(FilterParametersType.Industry())
            viewModel.updateFilters()
        }

        binding.workTerritories.setOnClickListener {
            findNavController().navigate(R.id.workTerritoriesFragment)
        }
        binding.workTerritoriesSelected.setOnClickListener {
            findNavController().navigate(R.id.workTerritoriesFragment)
        }
        binding.workTerritoryDelete.setOnClickListener {
            viewModel.deleteFilterParameter(FilterParametersType.Country())
            viewModel.deleteFilterParameter(FilterParametersType.Region())
            viewModel.updateFilters()
        }


    }

    private fun render(filters: FilterParameters) {
        binding.workTerritories.isVisible = filters.countryId == null
        binding.workTerritoriesSelected.isVisible = filters.countryId != null
        binding.workTerritoryName.text = makeWorkTerritoryName(filters)

        binding.industry.isVisible = filters.industryId == null
        binding.industrySelected.isVisible = filters.industryId != null
        binding.industryName.text = filters.industryName ?: ""


    }

    private fun makeWorkTerritoryName(filters: FilterParameters): String {
        if (filters.countryName != null && filters.regionName != null) {
            return "${filters.countryName}, ${filters.regionName}"
        } else if (filters.countryName != null) {
            return filters.countryName
        }
        return ""
    }
}
