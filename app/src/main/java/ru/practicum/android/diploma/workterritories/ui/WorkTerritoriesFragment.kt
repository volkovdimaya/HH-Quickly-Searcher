package ru.practicum.android.diploma.workterritories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkTerritoriesBinding
import ru.practicum.android.diploma.workterritories.presentation.WorkTerritoriesViewModel
import ru.practicum.android.diploma.workterritories.presentation.models.WorkTerritoriesState

class WorkTerritoriesFragment : Fragment() {

    private val args: WorkTerritoriesFragmentArgs by navArgs()

    private var _binding: FragmentWorkTerritoriesBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModel<WorkTerritoriesViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkTerritoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.country.setOnClickListener {
            findNavController().navigate(R.id.countriesFragment)
        }
        binding.region.setOnClickListener {
            findNavController().navigate(R.id.regionsFragment)
        }
        binding.countrySelectedDelete.setOnClickListener {
            viewModel.deleteCountry()
        }
        binding.regionSelectedDelete.setOnClickListener {
            viewModel.deleteRegion()
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is WorkTerritoriesState.SelectedArea -> {
                    showRegion(state)
                }
            }
        }
    }

    private fun showRegion(state: WorkTerritoriesState.SelectedArea) {
        state.country?.let {
            binding.country.visibility = View.GONE
            binding.countrySelected.visibility = View.VISIBLE
            binding.countrySelectedText.text = it.countryName

        } ?: run {
            binding.country.visibility = View.VISIBLE
            binding.countrySelected.visibility = View.GONE
        }

        state.region?.let {
            binding.region.visibility = View.GONE
            binding.regionSelected.visibility = View.VISIBLE
            binding.regionSelectedText.text = it.regionName

        } ?: run {
            binding.region.visibility = View.VISIBLE
            binding.regionSelected.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
