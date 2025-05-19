package ru.practicum.android.diploma.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.presentation.ShortVacancyListUiState
import ru.practicum.android.diploma.common.ui.ShortVacancyFragment
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.databinding.LayoutEmptyPlaceholderBinding
import ru.practicum.android.diploma.databinding.LayoutErrorVacancyPlaceholderBinding
import ru.practicum.android.diploma.favorites.presentation.FavoritesViewModel

class FavoritesFragment : ShortVacancyFragment<FragmentFavoritesBinding>() {

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
        _errorBinding = LayoutErrorVacancyPlaceholderBinding.inflate(layoutInflater)
        _emptyBinding = LayoutEmptyPlaceholderBinding.inflate(layoutInflater)
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

    override fun updateIncludeViewByProgressBar() {
        updateIncludeViewByClear()
    }

    override fun updateIncludeViewByEmpty() {
        updateIncludeView(emptyBinding.root)
    }

    override fun updateIncludeViewByError() {
        updateIncludeView(errorBinding.root)
    }

    override fun renderIncludeState(state: ShortVacancyListUiState.ShortVacancyListUiIncludeState) {
        // no states
    }

    override fun goToFragment(entityId: Int) {
        super.goToFragment(entityId)
        viewModel.restoreState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _errorBinding = null
        _emptyBinding = null
    }
}
