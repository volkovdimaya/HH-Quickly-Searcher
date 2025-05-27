package ru.practicum.android.diploma.regions.ui

import android.graphics.Region
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.common.ui.adapters.BaseAdapter
import ru.practicum.android.diploma.common.ui.fragments.ListWithSearchFragment
import ru.practicum.android.diploma.databinding.FragmentRegionsBinding

class RegionsFragment : Fragment() {
    private var _binding: FragmentRegionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegionsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    override val adapter: BaseAdapter<Region> = TODO()
//    override val navigateIdAction: Int = R.id.workTerritoriesFragment
//
//    override fun createBinding(
//        createBindingInflater: LayoutInflater,
//        container: ViewGroup?
//    ): FragmentRegionsBinding {
//        return FragmentRegionsBinding.inflate(createBindingInflater, container, false)
//    }
//
//    override fun onSearchTextChanged(toString: String) {
//        // todo
//    }
//
//    override fun performSearch() {
//        // todo
//    }
//
//    override fun renderIncludeState(state: ListUiState.ListUiIncludeState<Region>) {
//        TODO("Not yet implemented")
//    }

}
