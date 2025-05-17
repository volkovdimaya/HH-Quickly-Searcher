package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.ui.models.FilterParameters
import ru.practicum.android.diploma.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val args: SearchFragmentArgs by navArgs()
    private var filterParameters: FilterParameters? = null

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

        binding.editText.addTextChangedListener {
            val icon = if (it.isNullOrEmpty()) {
                R.drawable.ic_search
            } else {
                R.drawable.ic_cross
            }
            binding.textInputLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), icon)
        }
        binding.textInputLayout.setEndIconOnClickListener {
            val text = binding.editText.text
            if (!text.isNullOrEmpty()) {
                // todo
                binding.editText.text?.clear()
            }
        }

        /* B граф навигации заложен navArgs  @Parcelize класса
         FilterParameters с defaultValue="@null"
         для использования при необходимости  */

        filterParameters = args.filterParameters
        filterParameters?.let {
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
