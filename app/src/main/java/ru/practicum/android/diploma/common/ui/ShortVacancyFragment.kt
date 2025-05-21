package ru.practicum.android.diploma.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.presentation.ShortVacancyListUiState
import ru.practicum.android.diploma.util.CustomCircularProgressIndicator
import ru.practicum.android.diploma.util.SizeFormatter

abstract class ShortVacancyFragment<T : ViewBinding> : Fragment() {

    private var nullableBinding: T? = null
    val binding get() = nullableBinding!!

    private var _recyclerView: RecyclerView? = null
    val recyclerView get() = _recyclerView!!

    private var _includeView: ViewGroup? = null
    private val includeView get() = _includeView!!

    private val progressBar: ProgressBar by lazy { CustomCircularProgressIndicator(requireContext()).progressIndicator }
    abstract val adapter: ShortVacancyAdapter
    abstract val navigateIdAction: Int

    abstract fun createBinding(createBindingInflater: LayoutInflater, container: ViewGroup?): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        nullableBinding = createBinding(inflater, container)
        _includeView = binding.root.findViewById(R.id.include_view)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nullableBinding = null
        _includeView = null
    }

    open fun render(state: ShortVacancyListUiState) {
        when (state) {
            is ShortVacancyListUiState.AnyItem -> goToFragment(state.itemId)
            is ShortVacancyListUiState.Content -> updateIncludeViewByList(state.contentList)
            ShortVacancyListUiState.Default -> updateIncludeViewByClear()
            ShortVacancyListUiState.Empty -> updateIncludeViewByEmpty()
            ShortVacancyListUiState.Loading -> updateIncludeViewByProgressBar()
            ShortVacancyListUiState.Error -> updateIncludeViewByError()
            is ShortVacancyListUiState.ShortVacancyListUiIncludeState -> renderIncludeState(state)
        }
    }

    open fun goToFragment(entityId: String) {
        val bundleEntityId = Bundle().apply {
            putString(SHORT_VACANCY_EXTRA, entityId)

        }
        findNavController().navigate(navigateIdAction, bundleEntityId)

    }

    open fun initShortVacancyListView() {
        _recyclerView = RecyclerView(requireContext()).apply {
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = SizeFormatter.dpToPx(TOP_MARGIN_DP, context)
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    open fun updateIncludeViewByProgressBar() {
        updateIncludeView(progressBar)
    }

    open fun updateIncludeViewByList(list: List<VacancyShort>) {
        initShortVacancyListView()
        updateIncludeView(recyclerView)
        adapter.updateShortVacancyList(list)
    }

    open fun updateIncludeViewByClear() {
        includeView.removeAllViews()
    }

    open fun updateIncludeViewByEmpty() {
        updateIncludeViewByClear()
    }

    open fun updateIncludeViewByError() {
        updateIncludeViewByClear()
    }

    fun updateIncludeView(includedView: View?) {
        includeView.removeAllViews()
        includeView.addView(includedView)
    }

    abstract fun renderIncludeState(state: ShortVacancyListUiState.ShortVacancyListUiIncludeState)

    companion object {
        const val TOP_MARGIN_DP = 7f
        const val SHORT_VACANCY_EXTRA = "SHORT_VACANCY_EXTRA"
    }
}
