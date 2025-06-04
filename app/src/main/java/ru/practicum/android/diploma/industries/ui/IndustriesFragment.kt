package ru.practicum.android.diploma.industries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.presentation.FiltersUiState
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.common.ui.adapters.BaseAdapter
import ru.practicum.android.diploma.common.ui.fragments.ListWithSearchFragment
import ru.practicum.android.diploma.databinding.FragmentIndustriesBinding
import ru.practicum.android.diploma.databinding.LayoutIndustryEmptyPlaceholderBinding
import ru.practicum.android.diploma.industries.domain.models.Industry
import ru.practicum.android.diploma.industries.presentation.IndustriesViewModel

class IndustriesFragment : ListWithSearchFragment<Industry, FragmentIndustriesBinding>() {

    override val adapter: BaseAdapter<Industry> = IndustryAdapter()
    override val navigateIdAction: Int = R.id.filtersFragment
    private val viewModel: IndustriesViewModel by viewModel()

    private var _emptyBinding: LayoutIndustryEmptyPlaceholderBinding? = null
    private val emptyBinding get() = _emptyBinding!!

    override fun createBinding(
        createBindingInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIndustriesBinding {
        return FragmentIndustriesBinding.inflate(createBindingInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState.observe(viewLifecycleOwner) {
            buttonSelectRender(it is FiltersUiState.SelectPosition)
            render(it)
        }

        adapter.setOnItemClickListener = { industry ->
            hideKeyboard()
            viewModel.showSelectItem(industry)
        }

        binding.buttonSelect.setOnClickListener {
            viewModel.showAppropriateFragment()
        }

        binding.editText.setOnClickListener {
            binding.buttonSelect.visibility = View.GONE
        }
    }

    override fun initViews() {
        super.initViews()
        _emptyBinding = LayoutIndustryEmptyPlaceholderBinding.inflate(layoutInflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _emptyBinding = null
    }

    override fun updateIncludeViewByEmpty() {
        updateIncludeView(emptyBinding.root)
    }

    override fun renderIncludeState(state: ListUiState.ListUiIncludeState<Industry>) {
        when (state) {
            is FiltersUiState.FilterItem -> {
                saveFilterParameter(state.item)
            }
            is FiltersUiState.SelectPosition -> {
                showSelectPosition(state.newList)
            }
            FiltersUiState.NoChange -> {}
        }
    }

    private fun showSelectPosition(newList: List<Industry>) {
        adapter.updateList(newList)
        binding.buttonSelect.visibility = View.VISIBLE
    }

    private fun saveFilterParameter(item: Industry) {
        viewModel.saveFilterParameter(item)
    }

    override fun goToFragment(entityId: String) {
        findNavController().navigateUp()
    }

    override fun onSearchTextChanged(toString: String) {
        viewModel.onSearchTextChanged(toString.trim())
    }

    override fun performSearch() {
        //
    }

    private fun buttonSelectRender(isSelected: Boolean) {
        binding.buttonSelect.isVisible = isSelected
    }
}
