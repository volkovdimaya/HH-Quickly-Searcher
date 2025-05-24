package ru.practicum.android.diploma.common.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.viewbinding.ViewBinding
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.R

abstract class ListWithSearchFragment<T, V : ViewBinding> : ListWithInternetFragment<T, V>() {

    private var _searchText: EditText? = null
    private val searchText get() = _searchText!!

    private var _textInputLayout: TextInputLayout? = null
    private val textInputLayout get() = _textInputLayout!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchText.addTextChangedListener {
            setSearchIcon(it.isNullOrEmpty())
            onSearchTextChanged(it.toString())
        }

        textInputLayout.setEndIconOnClickListener {
            if (!searchText.text.isNullOrEmpty()) {
                searchText.text?.clear()
            }
        }

        searchText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                performSearch()
                true
            } else {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _textInputLayout = null
        _searchText = null

    }

    override fun initViews() {
        super.initViews()
        _textInputLayout = binding.root.findViewById(R.id.text_input_layout)
        _searchText = binding.root.findViewById(R.id.edit_text)
    }

    abstract fun onSearchTextChanged(toString: String)

    abstract fun performSearch()

    open fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchText.windowToken, 0)
    }

    private fun setSearchIcon(textFlag: Boolean) {
        val icon = if (textFlag) {
            R.drawable.ic_search
        } else {
            R.drawable.ic_cross
        }
        textInputLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), icon)
    }
}
