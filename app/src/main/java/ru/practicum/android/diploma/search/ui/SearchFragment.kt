package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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

        /* B граф навигации заложен navArgs  @Parcelize класса
         FilterParameters с defaultValue="@null"
         для использования при необходимости  */

        filterParameters = args.filterParameters
        filterParameters?.let {
        }
    }
}
