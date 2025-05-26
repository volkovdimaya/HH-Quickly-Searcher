package ru.practicum.android.diploma.common.ui.fragments

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
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.common.ui.adapters.BaseAdapter
import ru.practicum.android.diploma.util.CustomCircularProgressIndicator

abstract class BaseListFragment<T, V : ViewBinding> : Fragment() {

    var nullableBinding: V? = null
    val binding get() = nullableBinding!!

    private var _recyclerView: RecyclerView? = null
    val recyclerView get() = _recyclerView!!

    private var _includeView: ViewGroup? = null
    private val includeView get() = _includeView!!

    private val progressBar: ProgressBar by lazy { CustomCircularProgressIndicator(requireContext()).progressIndicator }
    abstract val adapter: BaseAdapter<T>
    abstract val navigateIdAction: Int

    abstract fun createBinding(createBindingInflater: LayoutInflater, container: ViewGroup?): V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        nullableBinding = createBinding(inflater, container)
        initViews()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nullableBinding = null
        _includeView = null
    }

    open fun initViews() {
        _includeView = binding.root.findViewById(R.id.include_view)
    }

    open fun render(state: ListUiState<T>) {
        when (state) {
            is ListUiState.AnyItem -> goToFragment(state.itemId)
            is ListUiState.Content<T> -> updateIncludeViewByList(state.contentList)
            ListUiState.Default -> updateIncludeViewByClear()
            ListUiState.Empty -> updateIncludeViewByEmpty()
            ListUiState.Loading -> updateIncludeViewByProgressBar()
            ListUiState.Error -> updateIncludeViewByError()
            is ListUiState.ListUiIncludeState -> renderIncludeState(state)
        }
    }

    open fun goToFragment(entityId: String) {
        val bundleEntityId = Bundle().apply {
            putString(SHORT_VACANCY_EXTRA, entityId)
        }
        findNavController().navigate(navigateIdAction, bundleEntityId)

    }

    open fun initListView() {
        _recyclerView = RecyclerView(requireContext()).apply {
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    open fun updateIncludeViewByProgressBar() {
        updateIncludeView(progressBar)
    }

    open fun updateIncludeViewByList(list: List<T>) {
        initListView()
        updateIncludeView(recyclerView)
        adapter.updateList(list)
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

    abstract fun renderIncludeState(state: ListUiState.ListUiIncludeState<T>)

    companion object {
        const val SHORT_VACANCY_EXTRA = "SHORT_VACANCY_EXTRA"
    }
}
