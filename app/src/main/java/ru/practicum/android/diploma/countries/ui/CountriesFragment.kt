package ru.practicum.android.diploma.countries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.countries.presentation.CountryViewModel
import ru.practicum.android.diploma.countries.presentation.models.CountryState
import ru.practicum.android.diploma.databinding.FragmentCountriesBinding

class CountriesFragment : Fragment() {

    private var _binding: FragmentCountriesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<CountryViewModel>()

    private val adapter = AdapterCountries()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = binding.countriesRv
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryState.Content -> updateListCountries(state.countries)
                CountryState.Loading -> {}
                CountryState.Error -> TODO()
            }
        }

    }

    private fun updateListCountries(countries: List<Country>) {
        adapter.updateCountries(countries)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
