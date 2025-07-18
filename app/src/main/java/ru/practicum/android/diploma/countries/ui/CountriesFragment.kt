package ru.practicum.android.diploma.countries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.common.presentation.FiltersUiState
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.common.ui.adapters.BaseAdapter
import ru.practicum.android.diploma.common.ui.fragments.ListWithInternetFragment
import ru.practicum.android.diploma.countries.presentation.CountryViewModel
import ru.practicum.android.diploma.databinding.FragmentCountriesBinding

class CountriesFragment : ListWithInternetFragment<Country, FragmentCountriesBinding>() {

    override val navigateIdAction: Int = R.id.workTerritoriesFragment
    override val adapter: BaseAdapter<Country> = CountriesAdapter()

    private val viewModel by viewModel<CountryViewModel>()

    override fun createBinding(createBindingInflater: LayoutInflater, container: ViewGroup?): FragmentCountriesBinding {
        return FragmentCountriesBinding.inflate(createBindingInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState.observe(viewLifecycleOwner) { state ->
            render(state)
        }

        adapter.setOnItemClickListener = {
            viewModel.showSelectItem(it)
        }

    }

    override fun renderIncludeState(state: ListUiState.ListUiIncludeState<Country>) {
        when (state) {
            is FiltersUiState.SuccessAddDb -> {
                findNavController().navigate(
                    navigateIdAction,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(findNavController().currentDestination!!.id, true)
                        .setLaunchSingleTop(true)
                        .build()
                )
            }
        }
    }

}
