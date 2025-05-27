package ru.practicum.android.diploma.workterritories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.databinding.FragmentWorkTerritoriesBinding
import ru.practicum.android.diploma.workterritories.presentation.WorkTerritoriesViewModel

class WorkTerritoriesFragment : Fragment() {

    private val viewModel: WorkTerritoriesViewModel by viewModel()

    private var _binding: FragmentWorkTerritoriesBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        binding.countrySelectedDelete.setOnClickListener {
            viewModel.deleteCountryFilter()
        }

        binding.regionSelectedDelete.setOnClickListener {
            viewModel.deleteRegionFilter()
        }

    }

    private fun updateScreen(workTerritory: WorkTerritory) {
        if (workTerritory.country == null) {
            binding.country.visibility = View.VISIBLE
            binding.countrySelected.visibility = View.GONE

        } else {
            binding.countryName.text = workTerritory.country.countryName
            binding.country.visibility = View.GONE
            binding.countrySelected.visibility = View.VISIBLE

        }

        if (workTerritory.regionWork == null) {
            binding.region.visibility = View.VISIBLE
            binding.regionSelected.visibility = View.GONE
        } else {
            binding.region.visibility = View.GONE
            binding.regionSelected.visibility = View.VISIBLE
            binding.regionName.text = workTerritory.regionWork.regionName
        }
    }

}
