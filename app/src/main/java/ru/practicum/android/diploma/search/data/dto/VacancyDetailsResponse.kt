package ru.practicum.android.diploma.search.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.regions.data.dto.AreaDto

class VacancyDetailsResponse(
    val id: String,
    val name: String,
    val description: String,
    val area: AreaDto,
    @SerializedName("salary_range")
    val salaryRange: SalaryDto? = null,
    val employer: EmployerDto? = null,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDto>? = null,
    val experience: ExperienceDto? = null,
    val employment: EmploymentFormDto? = null,
    @SerializedName("work_format")
    val workFormat: List<WorkFormatDto>? = null,
    val address: AddressDto? = null,
    @SerializedName("alternate_url")
    val alternateUrl: String? = null
) : Response()
