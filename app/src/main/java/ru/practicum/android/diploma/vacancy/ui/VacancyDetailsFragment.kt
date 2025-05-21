package ru.practicum.android.diploma.vacancy.ui

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.text.AnnotatedString
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
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.util.SizeFormatter
import ru.practicum.android.diploma.util.salaryFormatter
import ru.practicum.android.diploma.vacancy.domain.models.EmploymentType
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail
import ru.practicum.android.diploma.vacancy.presentation.api.VacancyDetailsScreenState
import ru.practicum.android.diploma.vacancy.presentation.api.VacancyDetailsViewModel
import androidx.core.view.get

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

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                viewModel.getScreenStateLiveData().observe(viewLifecycleOwner) { state ->
                    when (state) {
                        is VacancyDetailsScreenState.Data -> {
                            if (state.isFavourite) {
                                menu[R.id.action_favorite].setIcon(R.drawable.ic_favorites_on_24px)
                            } else {
                                menu[R.id.action_favorite].setIcon(R.drawable.ic_favorites_off_24px)
                            }
                        }
                        else -> {
                            menu[R.id.action_favorite].setIcon(R.drawable.ic_favorites_off_24px)
                            menu[R.id.action_favorite].isEnabled = false
                            menu[R.id.actionSharing].isEnabled = false
                        }
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_favorite -> {
                        viewModel.onFavouriteClick(vacancyId)
                        true
                    }
                    R.id.actionSharing -> {
                        shareApp()
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
        setHeading(vacancyDetails)
        setEmployerData(vacancyDetails)
        setEmploymentData(vacancyDetails)
        setDescription(vacancyDetails)
        setKeySkillsData(vacancyDetails)

        TODO("обработать isFavourite")
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
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, link)
            shareIntent.setType("text/plain")
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            requireContext().startActivity(
                Intent.createChooser(
                    shareIntent,
                    getString(R.string.sharing_title)
                )
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
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
            .transform(
                RoundedCorners(SizeFormatter.dpToPx(CORNER_RADIUS, requireContext())),
                CenterCrop()
            )
            .placeholder(R.drawable.ic_placeholder_32px)
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
            binding.vacancyInfo.setText(Html.fromHtml(
                vacancyDetails.description,
                Html.FROM_HTML_MODE_COMPACT
            ))
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

    private fun makeKeySkillsStr(list: List<String>): AnnotatedString {
        val bullet = "\u2022"
        val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
        val str = buildAnnotatedString {
            list.forEach {
                withStyle(style = paragraphStyle) {
                    append(bullet)
                    append("\t\t")
                    append(it)
                }
            }
        }
        Log.d("str", str.toString())
        return str
    }

    companion object {
        private const val CORNER_RADIUS = 12F
    }
}
