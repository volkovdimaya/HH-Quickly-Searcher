package ru.practicum.android.diploma.workterritories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.databinding.FragmentWorkTerritoriesBinding
import ru.practicum.android.diploma.workterritories.presentation.WorkTerritoriesViewModel

class WorkTerritoriesFragment : Fragment() {

    private val viewModel: WorkTerritoriesViewModel by viewModel()

    private var _binding: FragmentWorkTerritoriesBinding? = null
    val binding get() = _binding!!

    private var _topToolbar: Toolbar? = null
    private val topToolbar get() = _topToolbar!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _topToolbar = requireActivity().findViewById(R.id.top_toolbar)
        _binding = FragmentWorkTerritoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getWorkTerritoryLiveData().observe(viewLifecycleOwner) { workTerritory ->
            updateScreen(workTerritory)
        }

        binding.country.setOnClickListener {
            findNavController().navigate(R.id.countriesFragment)
        }

        binding.region.setOnClickListener {
            findNavController().navigate(R.id.regionsFragment)
        }

        binding.countrySelected.setOnClickListener {
            findNavController().navigate(R.id.countriesFragment)
        }

        binding.regionSelected.setOnClickListener {
            findNavController().navigate(R.id.regionsFragment)
        }

        binding.countrySelectedDelete.setOnClickListener {
            viewModel.deleteCountryAndRegionFilter()
        }

        binding.regionSelectedDelete.setOnClickListener {
            viewModel.deleteRegionFilter()
        }

        binding.buttonSelect.setOnClickListener {
            findNavController().navigateUp()
        }

        topToolbar.setNavigationOnClickListener {
            deleteWorkTerritoryParameters()
            findNavController().navigateUp()
        }

        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                deleteWorkTerritoryParameters()
                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)
    }

    override fun onDestroyView() {
        val appBarConfig = AppBarConfiguration(findNavController().graph)
        topToolbar.setupWithNavController(findNavController(), appBarConfig)
        _topToolbar = null
        super.onDestroyView()
    }

    private fun deleteWorkTerritoryParameters() {
        viewModel.deleteCountryAndRegionFilter()
    }

    private fun updateScreen(workTerritory: WorkTerritory) {
        val visibleControllerCountry = workTerritory.country != null
        val visibleControllerRegion = workTerritory.regionWork != null
        binding.country.isVisible = !visibleControllerCountry
        binding.countrySelected.isVisible = visibleControllerCountry

        binding.region.isVisible = !visibleControllerRegion
        binding.regionSelected.isVisible = visibleControllerRegion

        if (workTerritory.country !== null) {
            binding.countryName.text = workTerritory.country.countryName
        }
        if (workTerritory.regionWork != null) {
            binding.regionName.text = workTerritory.regionWork.regionName
        }

        val visibleButton = workTerritory.regionWork != null || workTerritory.country != null
        binding.buttonSelect.isVisible = visibleButton
    }
}
