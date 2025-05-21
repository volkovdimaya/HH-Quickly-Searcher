package ru.practicum.android.diploma.search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.presentation.ShortVacancyListUiState
import ru.practicum.android.diploma.common.ui.ShortVacancyFragment
import ru.practicum.android.diploma.common.ui.models.FilterParameters
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.databinding.ItemVacancyProgressbarBinding
import ru.practicum.android.diploma.databinding.LayoutErrorVacancyPlaceholderBinding
import ru.practicum.android.diploma.databinding.LayoutNoInternetBinding
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.util.TopSpacingItemDecoration

class SearchFragment : ShortVacancyFragment<FragmentSearchBinding>() {

    override val adapter = SearchAdapter()
    override val navigateIdAction: Int = R.id.vacancyDetailsFragment

    private val args: SearchFragmentArgs by navArgs()
    private var filterParameters: FilterParameters? = null

    private var _emptyBinding: LayoutErrorVacancyPlaceholderBinding? = null
    private val emptyBinding get() = _emptyBinding!!

    private var _noInternetErrorBinding: LayoutNoInternetBinding? = null
    private val noInternetErrorBinding get() = _noInternetErrorBinding!!

    private var _progressBarBinding: ItemVacancyProgressbarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private val viewModel by viewModel<SearchViewModel>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        _emptyBinding = LayoutErrorVacancyPlaceholderBinding.inflate(layoutInflater)
        _noInternetErrorBinding = LayoutNoInternetBinding.inflate(layoutInflater)
        _progressBarBinding = ItemVacancyProgressbarBinding.inflate(layoutInflater)
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenu()
        setupEditText()

        filterParameters = args.filterParameters

        viewModel.observeState.observe(viewLifecycleOwner) {
            render(it)
        }

        adapter.setOnItemClickListener = { vacancyShort ->
            viewModel.showVacancyDetails(vacancyShort)
        }
    }

    override fun initShortVacancyListView() {
        super.initShortVacancyListView()

        recyclerView.addItemDecoration(
            TopSpacingItemDecoration(
                resources.getDimensionPixelSize(R.dimen.search_recycler_top_spacing)
            )
        )
    }

    override fun updateIncludeViewByEmpty() {
        binding.imageSearchIdle.visibility = View.GONE
        binding.includeView.visibility = View.VISIBLE
        binding.responseHeader.visibility = View.VISIBLE
        binding.responseHeader.setText(R.string.response_search_empty)
        updateIncludeView(emptyBinding.root)
        hideKeyboard()
    }

    override fun updateIncludeViewByError() {
        binding.imageSearchIdle.visibility = View.GONE
        binding.includeView.visibility = View.VISIBLE
        binding.responseHeader.visibility = View.GONE
        updateIncludeView(noInternetErrorBinding.root)
        hideKeyboard()
    }

    override fun updateIncludeViewByList(list: List<VacancyShort>) {
        binding.imageSearchIdle.visibility = View.GONE
        binding.includeView.visibility = View.VISIBLE
        binding.responseHeader.visibility = View.VISIBLE

        val headerText = getString(R.string.search_result_count, list.size.toString()) // Поменять при пейджинге
        binding.responseHeader.text = headerText

        super.updateIncludeViewByList(list)
        hideKeyboard()
    }

    override fun updateIncludeViewByProgressBar() {
        binding.imageSearchIdle.visibility = View.GONE
        binding.includeView.visibility = View.VISIBLE
        binding.responseHeader.visibility = View.GONE
        updateIncludeView(progressBarBinding.root)
    }

    override fun updateIncludeViewByClear() {
        binding.imageSearchIdle.visibility = View.VISIBLE
        binding.includeView.visibility = View.GONE
        binding.responseHeader.visibility = View.GONE
        super.updateIncludeViewByClear()
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
        _emptyBinding = null
        _noInternetErrorBinding = null
        _progressBarBinding = null
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_fragment_toolbar_menu, menu)
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

    private fun setupEditText() {
        binding.editText.addTextChangedListener {
            val icon = if (it.isNullOrEmpty()) {
                R.drawable.ic_search
            } else {
                R.drawable.ic_cross
            }
            binding.textInputLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), icon)
            viewModel.onSearchTextChanged(it.toString())
        }

        binding.textInputLayout.setEndIconOnClickListener {
            val text = binding.editText.text
            if (!text.isNullOrEmpty()) {
                binding.editText.text?.clear()
            }
        }

        binding.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                performSearch()
                true
            } else {
                false
            }
        }
    }

    private fun performSearch() {
        val query = binding.editText.text.toString().trim()
        if (query.isNotEmpty()) {
            hideKeyboard()
            viewModel.updateRequest(query, filterParameters)
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editText.windowToken, 0)
    }
}
