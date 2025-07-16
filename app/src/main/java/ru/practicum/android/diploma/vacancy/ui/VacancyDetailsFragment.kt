package ru.practicum.android.diploma.vacancy.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.util.SizeFormatter
import ru.practicum.android.diploma.util.salaryFormatter
import ru.practicum.android.diploma.vacancy.domain.models.EmploymentType
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail
import ru.practicum.android.diploma.vacancy.presentation.api.VacancyDetailsScreenState
import ru.practicum.android.diploma.vacancy.presentation.api.VacancyDetailsViewModel

class VacancyDetailsFragment : Fragment() {

    private val args: VacancyDetailsFragmentArgs by navArgs()
    private val viewModel: VacancyDetailsViewModel by lazy { getViewModel { parametersOf(args.vacancyId) } }

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
        var iconActionFavorite: View? = null

        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.vacancy_details_fragment_toolbar_menu, menu)

                val menuItem = menu.findItem(R.id.action_favorite)
                val actionView = menuItem.actionView
                if (actionView != null) {
                    iconActionFavorite = actionView.findViewById<ImageView>(R.id.button_detail_menu)
                }
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)

                viewModel.isFavorite().observe(viewLifecycleOwner) {
                    iconActionFavorite?.isSelected = it
                }
                iconActionFavorite?.setOnClickListener {
                    viewModel.onFavouriteClick()
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_favorite -> {
                        viewModel.onFavouriteClick()
                        true
                    }

                    R.id.actionSharing -> {
                        shareApp()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.STARTED)

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

                is VacancyDetailsScreenState.Data -> renderData(state.vacancyDetails)
            }
        }
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
        vacancyDetails: VacancyDetail
    ) {
        binding.vacancyErrorImg.isVisible = false
        binding.vacancyErrorTxt.isVisible = false
        binding.progressBar.isVisible = false

        binding.vacancyDetailsLayout.isVisible = true
        setHeading(vacancyDetails)
        setEmployerData(vacancyDetails)
        setEmploymentData(vacancyDetails)
        setDescription(vacancyDetails)
        setKeySkillsData(vacancyDetails)
    }

    private fun makeWorkingHoursStr(employment: EmploymentType): String {
        var str = ""
        if (employment.employment.isNotEmpty()) {
            str += employment.employment
            if (employment.workFormat.isNotEmpty()) {
                str += ", " + employment.workFormat
            }
        } else {
            if (employment.workFormat.isNotEmpty()) {
                str += employment.workFormat
            }
        }
        return str
    }

    private fun shareApp() {
        val screenState = viewModel.getScreenStateLiveData().value
        if (screenState is VacancyDetailsScreenState.Data) {
            val link = screenState.vacancyDetails.vacancyUrl
            viewModel.shareVacancy(link)
        }
    }

    private fun setHeading(vacancyDetails: VacancyDetail) {
        binding.vacancyName.text = vacancyDetails.vacancyName
        val salary = salaryFormatter(vacancyDetails.salary, requireContext())
        if (salary.isNotEmpty()) {
            binding.vacancySalary.apply {
                text = salary
                isVisible = true
            }
        } else {
            binding.vacancySalary.isVisible = false
        }
    }

    private fun setEmployerData(vacancyDetails: VacancyDetail) {
        if (vacancyDetails.employer.isNotEmpty()) {
            binding.companyName.apply {
                text = vacancyDetails.employer
                isVisible = true
            }
        } else {
            binding.companyName.isVisible = false
        }
        Glide.with(this)
            .load(vacancyDetails.logoUrl)
            .error(R.drawable.ic_placeholder_48px)
            .placeholder(R.drawable.ic_placeholder_48px)
            .centerCrop()
            .transform(RoundedCorners(SizeFormatter.dpToPx(CORNER_RADIUS, requireContext())))
            .into(binding.companyLogo)
        binding.vacancyRegion.text = vacancyDetails.address.ifEmpty {
            vacancyDetails.workTerritory
        }
    }

    private fun setEmploymentData(vacancyDetails: VacancyDetail) {
        if (vacancyDetails.experience.isNotEmpty()) {
            binding.requiredExperience.apply {
                text = vacancyDetails.experience
                isVisible = true
            }
            binding.requiredExperienceTitle.isVisible = true
        } else {
            binding.requiredExperience.isVisible = false
            binding.requiredExperienceTitle.isVisible = false
        }
        val employment = makeWorkingHoursStr(vacancyDetails.employment)
        if (employment.isNotEmpty()) {
            binding.workingHours.apply {
                text = employment
                isVisible = true
            }
        } else {
            binding.workingHours.isVisible = false
        }
    }

    private fun setDescription(vacancyDetails: VacancyDetail) {
        if (vacancyDetails.description.isNotEmpty()) {
            binding.vacancyInfo.setText(
                Html.fromHtml(
                    vacancyDetails.description,
                    Html.FROM_HTML_MODE_COMPACT
                ).trimEnd()
            )
            binding.vacancyInfo.isVisible = true
            binding.vacancyInfoTitle.isVisible = true
        } else {
            binding.vacancyInfo.isVisible = false
            binding.vacancyInfoTitle.isVisible = false
        }
    }

    private fun setKeySkillsData(vacancyDetails: VacancyDetail) {
        if (vacancyDetails.keySkills.isNotEmpty()) {
            binding.vacancyKeySkills.text = makeKeySkillsStr(vacancyDetails.keySkills)
            binding.vacancyKeySkills.isVisible = true
            binding.vacancyKeySkillsTitle.isVisible = true
        } else {
            binding.vacancyKeySkills.isVisible = false
            binding.vacancyKeySkillsTitle.isVisible = false
        }
    }

    private fun makeKeySkillsStr(list: List<String>): String {
        val bullet = "\u2022"
        val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
        val str = buildAnnotatedString {
            list.forEach {
                withStyle(style = paragraphStyle) {
                    append(bullet)
                    append("\t\t")
                    append(it)
                    append("\n")
                }
            }
        }
        return str.toString().trimEnd()
    }

    companion object {
        private const val CORNER_RADIUS = 12F
    }
}
