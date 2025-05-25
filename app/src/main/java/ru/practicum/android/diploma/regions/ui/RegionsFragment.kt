package ru.practicum.android.diploma.regions.ui

import android.graphics.Region
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.common.ui.adapters.BaseAdapter
import ru.practicum.android.diploma.common.ui.fragments.ListWithSearchFragment
import ru.practicum.android.diploma.databinding.FragmentRegionsBinding

class RegionsFragment : ListWithSearchFragment<Region, FragmentRegionsBinding>() {

    override val adapter: BaseAdapter<Region> = TODO()
    override val navigateIdAction: Int = R.id.workTerritoriesFragment

    override fun createBinding(
        createBindingInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegionsBinding {
        return FragmentRegionsBinding.inflate(createBindingInflater, container, false)
    }

    override fun onSearchTextChanged(toString: String) {
        // todo
    }

    override fun performSearch() {
        // todo
    }

    override fun renderIncludeState(state: ListUiState.ListUiIncludeState<Region>) {
        TODO("Not yet implemented")
    }

}
