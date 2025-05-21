package ru.practicum.android.diploma.vacancy.presentation.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor

class VacancyDetailsViewModel(
    private val interactor: VacancyDetailsInteractor
) : ViewModel() {

    private val screenStateLiveData = MutableLiveData<VacancyDetailsScreenState>(VacancyDetailsScreenState.Loading)
    fun getScreenStateLiveData(): LiveData<VacancyDetailsScreenState> = screenStateLiveData

    fun getVacancyDetails(vacancyId: String) {
        screenStateLiveData.postValue(VacancyDetailsScreenState.Loading)
        viewModelScope.launch {
            var isFavourite = false
            interactor.isVacancyFavourite(vacancyId).collect { isVacancyFavourite ->
                isFavourite = isVacancyFavourite
                Log.d("666", isFavourite.toString())
            }
            interactor.getVacancyDetails(vacancyId, isFavourite).collect { detailsResponse ->
                val code = detailsResponse.resultCode
                Log.d("666", detailsResponse.vacancyDetail.toString())
                if (code == SUCCESS_CODE && !detailsResponse.vacancyDetail.isNullOrEmpty()) {
                    screenStateLiveData.postValue(VacancyDetailsScreenState.Data(
                        detailsResponse.vacancyDetail[0],
                        isFavourite
                    ))
                } else if (code == NOT_FOUND_CODE || detailsResponse.vacancyDetail.isNullOrEmpty()) {
                    screenStateLiveData.postValue(VacancyDetailsScreenState.NothingFound)
                } else {
                    screenStateLiveData.postValue(VacancyDetailsScreenState.ServerError)
                }
            }
        }
    }

    fun onFavouriteClick(vacancyId: String) {
        val currentScreenState = screenStateLiveData.value
        if (currentScreenState is VacancyDetailsScreenState.Data) {
            var currentFavouriteState = false
            viewModelScope.launch {
                interactor.isVacancyFavourite(vacancyId).collect {
                    currentFavouriteState = it
                }
                if (currentFavouriteState) {
                    interactor.deleteFavourite(currentScreenState.vacancyDetails).collect { code ->
                        when (code) {
                            INTERNAL_ERROR_CODE -> {}
                            else -> {}
                        }
                    }
                } else {
                    interactor.addFavourite(currentScreenState.vacancyDetails).collect { code ->
                        when (code) {
                            INTERNAL_ERROR_CODE -> {}
                            else -> {}
                        }
                    }
                }
            }
            screenStateLiveData.postValue(VacancyDetailsScreenState.Data(
                currentScreenState.vacancyDetails,
                !currentFavouriteState
            ))
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
        private const val NOT_FOUND_CODE = 404
        const val INTERNAL_ERROR_CODE = 500
    }
}
