package ru.practicum.android.diploma.industries.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.presentation.FiltersUiState
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.common.ui.adapters.BaseAdapter
import ru.practicum.android.diploma.common.ui.fragments.ListWithSearchFragment
import ru.practicum.android.diploma.databinding.FragmentIndustriesBinding
import ru.practicum.android.diploma.industries.domain.models.Industry
import ru.practicum.android.diploma.industries.presentation.IndustriesViewModel

class IndustriesFragment : ListWithSearchFragment<Industry, FragmentIndustriesBinding>() {

    override val adapter: BaseAdapter<Industry> = IndustryAdapter()
    override val navigateIdAction: Int = R.id.filtersFragment
    private val viewModel: IndustriesViewModel by viewModel()

    override fun createBinding(
        createBindingInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIndustriesBinding {
        return FragmentIndustriesBinding.inflate(createBindingInflater, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState.observe(viewLifecycleOwner) {
            render(it)
        }

        adapter.setOnItemClickListener = { industry ->
            hideKeyboard()
            viewModel.showSelectButton(industry)
        }

        binding.buttonSelect.setOnClickListener {
            viewModel.showAppropriateFragment()
        }

        binding.editText.setOnClickListener{

                binding.buttonSelect.visibility = View.GONE

        }
    }

    override fun renderIncludeState(state: ListUiState.ListUiIncludeState<Industry>) {
        when (state) {
            is FiltersUiState.FilterItem -> {
                returnToPreviousFragment(state.item)
            }
            is FiltersUiState.SelectPosition -> {
                showSelectPosition(state.newList)
            }

        }
    }

    private fun showSelectPosition(newList: List<Industry>) {
        updateIncludeViewByList(newList)
        binding.buttonSelect.visibility = View.VISIBLE
    }

    fun visibilitySelectButton(flag: Boolean) {
        if (flag) {
            binding.buttonSelect.visibility = View.GONE
        }
    }

    private fun returnToPreviousFragment(item: Industry) {
        val industryParameterUi = item.toParcelable()
        val directions = IndustriesFragmentDirections.actionIndustriesFragmentToFiltersFragment(industryParameterUi)
        findNavController().navigate(directions)
        viewModel.restoreState()
    }

    override fun onSearchTextChanged(toString: String) {

        viewModel.onSearchTextChanged(toString)
    }

    override fun performSearch() {
        //visibilitySelectButton(true)
    }

    override fun hideKeyboard() {
        super.hideKeyboard()
     //   if (ime.)
    }
}
