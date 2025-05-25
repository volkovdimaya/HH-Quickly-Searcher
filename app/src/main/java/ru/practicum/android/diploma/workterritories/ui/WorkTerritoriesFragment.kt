package ru.practicum.android.diploma.workterritories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkTerritoriesBinding

class WorkTerritoriesFragment : Fragment() {

    private val args: WorkTerritoriesFragmentArgs by navArgs()

    private var _binding: FragmentWorkTerritoriesBinding? = null

    private val binding get() = _binding!!


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
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
