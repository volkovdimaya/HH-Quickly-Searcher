package ru.practicum.android.diploma.vacancy.presentation.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor

class VacancyDetailsViewModel(
    private val interactor: VacancyDetailsInteractor
) : ViewModel() {

    private val screenStateLiveData = MutableLiveData<VacancyDetailsScreenState>(VacancyDetailsScreenState.Loading)
    fun getScreenStateLiveData(): LiveData<VacancyDetailsScreenState> = screenStateLiveData

    fun getVacancyDetails(vacancyId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var isFavourite = false
                interactor.isVacancyFavourite(vacancyId).collect { isVacancyFavourite ->
                    isFavourite = isVacancyFavourite
                }
                interactor.getVacancyDetails(vacancyId, isFavourite).collect { detailsResponse ->
                    val code = detailsResponse.resultCode
                    if (code == SUCCESS_CODE && !detailsResponse.vacancyDetail.isNullOrEmpty()) {
                        screenStateLiveData.postValue(
                            VacancyDetailsScreenState.Data(
                                detailsResponse.vacancyDetail[0],
                                isFavourite
                            )
                        )
                    } else if (code == NOT_FOUND_CODE || detailsResponse.vacancyDetail.isNullOrEmpty()) {
                        screenStateLiveData.postValue(VacancyDetailsScreenState.NothingFound)
                    } else {
                        screenStateLiveData.postValue(VacancyDetailsScreenState.ServerError)
                    }
                }
            }
        }
    }

    fun onFavouriteClick() {
        val currentScreenState = screenStateLiveData.value
        if (currentScreenState is VacancyDetailsScreenState.Data) {
            val currentFavouriteState = currentScreenState.isFavourite
            viewModelScope.launch {
                if (currentFavouriteState) {
                    deleteFavorite(currentScreenState)
                } else {
                    addFavorite(currentScreenState)
                }
            }
            screenStateLiveData.postValue(VacancyDetailsScreenState.Data(
                currentScreenState.vacancyDetails,
                !currentFavouriteState
            ))
        }
    }

    fun shareVacancy(link: String) {
        interactor.shareVacancy(link)
    }

    private suspend fun deleteFavorite(currentScreenState: VacancyDetailsScreenState.Data) {
        interactor.deleteFavourite(currentScreenState.vacancyDetails).collect { code ->
            when (code) {
                INTERNAL_ERROR_CODE -> {}
                else -> {}
            }
        }
    }

    private suspend fun addFavorite(currentScreenState: VacancyDetailsScreenState.Data) {
        interactor.addFavourite(currentScreenState.vacancyDetails).collect { code ->
            when (code) {
                INTERNAL_ERROR_CODE -> {}
                else -> {}
            }
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
        private const val NOT_FOUND_CODE = 404
        const val INTERNAL_ERROR_CODE = 500
    }
}
