package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.ui.models.FilterParameters
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.presentation.ViewModelSearch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val args: SearchFragmentArgs by navArgs()
    private var filterParameters: FilterParameters? = null

    private val viewModel by viewModel<ViewModelSearch>()


//    private val progressBar by lazy {
//        binding.loading
//    }
//    private val idle by lazy {
//        binding.imageSearchIdle
//    }
//    private val noInternet by lazy {
//        binding.noInternet
//    }
//    private val noContent by lazy {
//        binding.responseEmpty
//    }
//    private val listVacancy by lazy {
//        binding.layoutListVacancy
//    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        /* B граф навигации заложен navArgs  @Parcelize класса
         FilterParameters с defaultValue="@null"
         для использования при необходимости  */

        viewModel.updateRequest("Android")

        filterParameters = args.filterParameters
        filterParameters?.let {
        }
    }
}
