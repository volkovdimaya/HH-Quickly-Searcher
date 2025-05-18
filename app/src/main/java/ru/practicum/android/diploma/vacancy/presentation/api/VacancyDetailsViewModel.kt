package ru.practicum.android.diploma.vacancy.presentation.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

class VacancyDetailsViewModel(
    private val interactor: VacancyDetailsInteractor
) :ViewModel() {

    private val screenStateLiveData = MutableLiveData<VacancyDetailsScreenState>(VacancyDetailsScreenState.Loading)
    fun getScreenStateLiveData(): LiveData<VacancyDetailsScreenState> = screenStateLiveData

    fun getVacancyDetails(vacancyId: Int) {
        viewModelScope.launch {
            var details: VacancyDetail? = null
            var isFavourite = false
            try {
                interactor.isVacancyFavourite(vacancyId).collect { isVacancyFavourite ->
                    isFavourite = isVacancyFavourite
                }
                interactor.getVacancyDetails(vacancyId, isFavourite).collect { vacancyDetails ->
                    details = vacancyDetails
                }
                screenStateLiveData.postValue(VacancyDetailsScreenState.Data(details!!, isFavourite))
            } catch (e: Exception) {
                screenStateLiveData.postValue(VacancyDetailsScreenState.ServerError)
            }
            // TODO Добавить нормальную обработку ошибок

        }
    }

    fun onFavouriteClick(vacancyId: Int) {
        // TODO реализовать
    }
}
