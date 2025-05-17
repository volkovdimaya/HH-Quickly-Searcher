package ru.practicum.android.diploma.common.domain.models



open class VacancyShort(
    val id: String,
    val name: String,
    val city: String,
    val salary: Salary?,
    val logoUrl: String,
    val employer: String
){
    override fun toString(): String {
        return "VacancyShort(id='$id', name='$name', city='$city', salary=$salary, logoUrl='$logoUrl', employer='$employer')"
    }
}
