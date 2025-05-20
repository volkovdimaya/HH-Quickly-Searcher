package ru.practicum.android.diploma.vacancy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail
import ru.practicum.android.diploma.vacancy.presentation.api.VacancyDetailsScreenState
import ru.practicum.android.diploma.vacancy.presentation.api.VacancyDetailsViewModel

class VacancyDetailsFragment : Fragment() {

    private val viewModel by viewModel<VacancyDetailsViewModel>()
    private val args: VacancyDetailsFragmentArgs by navArgs()

    private var _binding: FragmentVacancyDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        val vacancyId = args.vacancyId

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.vacancy_details_fragment_toolbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_favorite -> {
                        viewModel.onFavouriteClick(vacancyId)
                        true
                    }
                    R.id.actionSharing -> {
                        // todo
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.getScreenStateLiveData().observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyDetailsScreenState.Loading -> renderLoadingState()
                is VacancyDetailsScreenState.ServerError -> renderErrorState(
                    getString(R.string.server_error),
                    R.drawable.img_vacancy_server_error
                )
                is VacancyDetailsScreenState.NothingFound -> renderErrorState(
                    getString(R.string.vacancy_was_not_found_or_deleted),
                    R.drawable.img_vacancy_is_not_avaliable
                )
                is VacancyDetailsScreenState.Data -> renderData(state.vacancyDetails, state.isFavourite)
            }
        }
        viewModel.getVacancyDetails(vacancyId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderLoadingState() {
        binding.vacancyDetailsLayout.isVisible = false
        binding.vacancyErrorImg.isVisible = false
        binding.vacancyErrorTxt.isVisible = false

        binding.progressBar.isVisible = true
    }

    private fun renderErrorState(
        errorText: String,
        errorDrawable: Int
    ) {
        binding.vacancyDetailsLayout.isVisible = false
        binding.progressBar.isVisible = false

        binding.vacancyErrorImg.setImageResource(errorDrawable)
        binding.vacancyErrorTxt.text = errorText
        binding.vacancyErrorImg.isVisible = true
        binding.vacancyErrorTxt.isVisible = true
    }

    private fun renderData(
        vacancyDetails: VacancyDetail,
        isFavourite: Boolean
    ) {
        binding.vacancyErrorImg.isVisible = false
        binding.vacancyErrorTxt.isVisible = false
        binding.progressBar.isVisible = false

        binding.vacancyDetailsLayout.isVisible = true
        binding.vacancyName.text = vacancyDetails.vacancyName
        TODO("доделать все данные")
    }
}
