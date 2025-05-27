package ru.practicum.android.diploma.workterritories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.practicum.android.diploma.R

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

        binding.button.setOnClickListener {
            viewModel.createfilt()
        }

    }

    private fun updateScreen(workTerritory: WorkTerritory) {
        Log.d("workt","${workTerritory.country}")
        if (workTerritory.country == null) {
            binding.country.visibility = View.VISIBLE
            binding.countrySelected.visibility = View.GONE
            vvv(workTerritory)
        } else {
            binding.countryName.text = workTerritory.country.countryName
            binding.country.visibility = View.GONE
            binding.countrySelected.visibility = View.VISIBLE
            vvv(workTerritory)
        }

binding.root.requestLayout()
    }

    fun vvv(workTerritory: WorkTerritory) {
        if (workTerritory.regionWork == null) {
            binding.region.visibility = View.VISIBLE
            binding.region.requestLayout()
            binding.regionSelected.visibility = View.GONE
        } else {
            binding.region.visibility = View.GONE
            binding.regionSelected.visibility = View.VISIBLE
            binding.chooseRegion.text = workTerritory.regionWork.regionName
        }
    }

}
