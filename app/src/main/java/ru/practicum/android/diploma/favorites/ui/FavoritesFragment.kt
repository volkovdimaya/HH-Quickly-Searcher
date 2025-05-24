package ru.practicum.android.diploma.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.common.ui.fragments.BaseListFragment
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.databinding.LayoutEmptyPlaceholderBinding
import ru.practicum.android.diploma.databinding.LayoutErrorVacancyPlaceholderBinding
import ru.practicum.android.diploma.favorites.presentation.FavoritesViewModel
import ru.practicum.android.diploma.util.SizeFormatter

class FavoritesFragment : BaseListFragment<VacancyShort, FragmentFavoritesBinding>() {

    override val adapter = FavoritesAdapter()
    override val navigateIdAction: Int = R.id.vacancyDetailsFragment

    private var _errorBinding: LayoutErrorVacancyPlaceholderBinding? = null
    private val errorBinding get() = _errorBinding!!

    private var _emptyBinding: LayoutEmptyPlaceholderBinding? = null
    private val emptyBinding get() = _emptyBinding!!

    private val viewModel: FavoritesViewModel by viewModel()

    override fun createBinding(
        createBindingInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(createBindingInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState.observe(viewLifecycleOwner) {
            render(it)
        }

        adapter.setOnItemClickListener = { vacancyShort ->
            viewModel.showVacancyDetails(vacancyShort)

        }
    }

    override fun initViews() {
        super.initViews()
        _errorBinding = LayoutErrorVacancyPlaceholderBinding.inflate(layoutInflater)
        _emptyBinding = LayoutEmptyPlaceholderBinding.inflate(layoutInflater)
    }

    override fun initShortVacancyListView() {
        super.initShortVacancyListView()
        recyclerView.apply {
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = SizeFormatter.dpToPx(TOP_MARGIN_DP, context)
                marginStart = SizeFormatter.dpToPx(START_MARGIN_DP, context)
            }
        }
    }

    override fun updateIncludeViewByEmpty() {
        updateIncludeView(emptyBinding.root)
    }

    override fun updateIncludeViewByError() {
        updateIncludeView(errorBinding.root)
    }

    override fun renderIncludeState(state: ListUiState.ListUiIncludeState<VacancyShort>) {
        // no states
    }

    override fun goToFragment(entityId: String) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToVacancyDetailsFragment(entityId)
        findNavController().navigate(action)
        viewModel.restoreState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _errorBinding = null
        _emptyBinding = null
    }

    companion object {
        const val TOP_MARGIN_DP = 7f
        const val START_MARGIN_DP = 16f
    }
}
