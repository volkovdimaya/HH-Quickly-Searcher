package ru.practicum.android.diploma.regions.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.presentation.FiltersUiState
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.common.ui.adapters.BaseAdapter
import ru.practicum.android.diploma.common.ui.fragments.ListWithSearchFragment
import ru.practicum.android.diploma.databinding.FragmentRegionsBinding
import ru.practicum.android.diploma.databinding.LayoutRegionEmptyPlaceholderBinding
import ru.practicum.android.diploma.regions.domain.models.Region
import ru.practicum.android.diploma.regions.presentation.RegionsViewModel

class RegionsFragment : ListWithSearchFragment<Region, FragmentRegionsBinding>() {

    override val adapter: BaseAdapter<Region> = RegionsAdapter()
    override val navigateIdAction: Int = R.id.workTerritoriesFragment

    private val viewModel: RegionsViewModel by viewModel()

    private var _emptyBinding: LayoutRegionEmptyPlaceholderBinding? = null
    private val emptyBinding get() = _emptyBinding!!

    override fun initViews() {
        super.initViews()
        _emptyBinding = LayoutRegionEmptyPlaceholderBinding.inflate(layoutInflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _emptyBinding = null
    }

    override fun updateIncludeViewByEmpty() {
        updateIncludeView(emptyBinding.root)
    }

    override fun createBinding(
        createBindingInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegionsBinding {
        return FragmentRegionsBinding.inflate(createBindingInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState.observe(viewLifecycleOwner) {
            render(it)
        }

        adapter.setOnItemClickListener = { region ->
            viewModel.saveFilterParameter(region)
        }
    }

    override fun renderIncludeState(state: ListUiState.ListUiIncludeState<Region>) {
        when (state) {
            is FiltersUiState.FilterItem -> {
                findNavController().navigate(
                    navigateIdAction,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(navigateIdAction, true)
                        .setLaunchSingleTop(true)
                        .build()
                )
            }
            FiltersUiState.NoChange -> {}
        }
    }

    override fun onSearchTextChanged(toString: String) {
        viewModel.onSearchTextChanged(toString.trim())
    }

    override fun performSearch() {
        // todo
    }
}
