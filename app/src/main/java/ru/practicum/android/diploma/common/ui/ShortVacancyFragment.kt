package ru.practicum.android.diploma.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.practicum.android.diploma.R

abstract class ShortVacancyFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    private val binding get() = _binding!!

    private var _recyclerView: RecyclerView? = null
    private val recyclerView get() = _recyclerView!!

    //   private val progressBar: ProgressBar by lazy { CustomCircularProgressIndicator(requireContext()) }
    abstract val adapter: ShortVacancyAdapter

    private var _includeView: ViewGroup? = null
    private val includeView get() = _includeView!!

    abstract fun createBinding(increateBindingflater: LayoutInflater, container: ViewGroup?): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = createBinding(inflater, container)
        _includeView = binding.root.findViewById(R.id.include_view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _includeView = null
    }
}
