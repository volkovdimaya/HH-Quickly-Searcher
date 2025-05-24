package ru.practicum.android.diploma.countries.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.common.ui.adapters.BaseAdapter
import ru.practicum.android.diploma.common.ui.fragments.ListWithInternetFragment
import ru.practicum.android.diploma.databinding.FragmentCountriesBinding

class CountriesFragment : ListWithInternetFragment<Country, FragmentCountriesBinding>() {

    override val adapter: BaseAdapter<Country>
        get() = TODO("Not yet implemented")
    override val navigateIdAction: Int = R.id.workTerritoriesFragment

    override fun createBinding(
        createBindingInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCountriesBinding {
        return FragmentCountriesBinding.inflate(createBindingInflater, container, false)
    }

    override fun renderIncludeState(state: ListUiState.ListUiIncludeState<Country>) {
        TODO("Not yet implemented")
    }
}
