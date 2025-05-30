package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.common.presentation.ShortVacancyListUiState
import ru.practicum.android.diploma.common.ui.fragments.ShortVacancyFragment
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.databinding.ItemVacancyProgressbarBinding
import ru.practicum.android.diploma.databinding.LayoutErrorVacancyPlaceholderBinding
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.util.TopSpacingItemDecoration

class SearchFragment : ShortVacancyFragment<FragmentSearchBinding>() {

    override val adapter = SearchAdapter()
    override val navigateIdAction: Int = R.id.vacancyDetailsFragment

    private var _emptyBinding: LayoutErrorVacancyPlaceholderBinding? = null
    private val emptyBinding get() = _emptyBinding!!

    private var _progressBarBinding: ItemVacancyProgressbarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private val viewModel by viewModel<SearchViewModel>()

    private var needToInitRecyclerView = true

    override fun createBinding(
        createBindingInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(createBindingInflater, container, false)
    }

    override fun initViews() {
        super.initViews()
        _emptyBinding = LayoutErrorVacancyPlaceholderBinding.inflate(layoutInflater)
        _progressBarBinding = ItemVacancyProgressbarBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenu()

        viewModel.observeState.observe(viewLifecycleOwner) {
            render(it)
        }

        adapter.setOnItemClickListener = { vacancyShort ->
            viewModel.showVacancyDetails(vacancyShort)
        }

        viewModel.getFilters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _emptyBinding = null
        _progressBarBinding = null
    }

    override fun onSearchTextChanged(toString: String) {
        viewModel.onSearchTextChanged(toString)
    }

    override fun initShortVacancyListView() {
        super.initShortVacancyListView()

        recyclerView.addItemDecoration(
            TopSpacingItemDecoration(
                resources.getDimensionPixelSize(R.dimen.search_recycler_top_spacing)
            )
        )

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    if (lastVisibleItemPosition >= totalItemCount - LOAD_MORE_THRESHOLD && !adapter.isLoadingMore) {
                        viewModel.onLastItemReached()
                    }
                }
            }
        })

        needToInitRecyclerView = false
    }

    override fun render(state: ListUiState<VacancyShort>) {
        when (state) {
            is ShortVacancyListUiState.AnyItem -> goToFragment(state.itemId)
            is ShortVacancyListUiState.ContentWithMetadata -> updateIncludeViewByContentWithMetadata(state)
            is ShortVacancyListUiState.NewItems -> addNewItems(state)
            ShortVacancyListUiState.Default -> updateIncludeViewByClear()
            ShortVacancyListUiState.Empty -> updateIncludeViewByEmpty()
            ShortVacancyListUiState.Loading -> updateIncludeViewByProgressBar()
            ShortVacancyListUiState.Error -> updateIncludeViewByError()
            ShortVacancyListUiState.ServerError -> updateIncludeViewByServerError()
            is ShortVacancyListUiState.LoadingMore -> showLoadingMore()
            is ShortVacancyListUiState.LoadingMoreError -> handleLoadingMoreError()
            is ShortVacancyListUiState.Content -> updateIncludeViewByList(state.contentList)
            is ShortVacancyListUiState.ShortVacancyListUiIncludeState -> renderIncludeState(state)
        }
    }

    private fun addNewItems(state: ShortVacancyListUiState.NewItems) {
        val headerText = getString(R.string.search_result_count, state.totalFound.toString())
        binding.responseHeader.text = headerText

        adapter.addItems(state.newItems)
        adapter.setLoadingMore(false)
        adapter.updateShortVacancyListNewItems()
    }

    private fun updateIncludeViewByContentWithMetadata(state: ShortVacancyListUiState.ContentWithMetadata) {
        binding.imageSearchIdle.visibility = View.GONE
        binding.includeView.visibility = View.VISIBLE
        binding.responseHeader.visibility = View.VISIBLE

        val headerText = getString(R.string.search_result_count, state.totalFound.toString())
        binding.responseHeader.text = headerText

        needToInitRecyclerView = true
        updateIncludeViewByList(state.contentList)

        adapter.setLoadingMore(false)
        hideKeyboard()
    }

    private fun showLoadingMore() {
        adapter.setLoadingMore(true)
    }

    private fun handleLoadingMoreError() {
        adapter.setLoadingMore(false)

        Toast.makeText(
            requireContext(),
            R.string.paging_network_error,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun updateIncludeViewByEmpty() {
        binding.imageSearchIdle.visibility = View.GONE
        binding.includeView.visibility = View.VISIBLE
        binding.responseHeader.visibility = View.VISIBLE
        binding.responseHeader.setText(R.string.response_search_empty)
        updateIncludeView(emptyBinding.root)
        hideKeyboard()
        needToInitRecyclerView = true
    }

    override fun updateIncludeViewByError() {
        binding.imageSearchIdle.visibility = View.GONE
        binding.includeView.visibility = View.VISIBLE
        binding.responseHeader.visibility = View.GONE
        updateIncludeView(noInternetErrorBinding.root)
        hideKeyboard()
        needToInitRecyclerView = true
    }

    override fun updateIncludeViewByList(list: List<VacancyShort>) {
        binding.imageSearchIdle.visibility = View.GONE
        binding.includeView.visibility = View.VISIBLE
        binding.responseHeader.visibility = View.VISIBLE

        if (needToInitRecyclerView) {
            initShortVacancyListView()
            updateIncludeView(recyclerView)
            needToInitRecyclerView = false
        }

        adapter.updateShortVacancyList(list)
        hideKeyboard()
    }

    override fun updateIncludeViewByProgressBar() {
        binding.imageSearchIdle.visibility = View.GONE
        binding.includeView.visibility = View.VISIBLE
        binding.responseHeader.visibility = View.GONE
        updateIncludeView(progressBarBinding.root)

        needToInitRecyclerView = true
    }

    override fun updateIncludeViewByClear() {
        binding.imageSearchIdle.visibility = View.VISIBLE
        binding.includeView.visibility = View.GONE
        binding.responseHeader.visibility = View.GONE
        super.updateIncludeViewByClear()

        needToInitRecyclerView = true
    }

    override fun renderIncludeState(state: ListUiState.ListUiIncludeState<VacancyShort>) {
        // no states
    }

    override fun goToFragment(entityId: String) {
        val directions = SearchFragmentDirections.actionSearchFragmentToVacancyDetailsFragment(entityId)
        findNavController().navigate(directions)
        viewModel.restoreState()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            var iconActionFilters: View? = null

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_fragment_toolbar_menu, menu)
                val menuItem = menu.findItem(R.id.action_filters_fragment)

                val actionView = menuItem.actionView
                if (actionView != null) {
                    iconActionFilters = actionView.findViewById<ImageView>(R.id.button_filter_menu)
                }
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                viewModel.isFiltersEmpty().observe(viewLifecycleOwner) {
                    iconActionFilters?.isSelected = !it
                }

                iconActionFilters?.setOnClickListener {
                    findNavController().navigate(R.id.filtersFragment)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_filters_fragment -> {
                        findNavController().navigate(R.id.filtersFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun performSearch() {
        val query = binding.editText.text.toString().trim()
        if (query.isNotEmpty()) {
            hideKeyboard()
            viewModel.updateRequest(query)
        }
    }

    companion object {
        private const val LOAD_MORE_THRESHOLD = 3
    }
}
