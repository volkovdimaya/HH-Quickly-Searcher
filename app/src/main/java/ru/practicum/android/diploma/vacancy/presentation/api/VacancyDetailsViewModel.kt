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
    vacancyId: String,
    private val interactor: VacancyDetailsInteractor
) : ViewModel() {

    private val screenStateLiveData = MutableLiveData<VacancyDetailsScreenState>(VacancyDetailsScreenState.Loading)
    fun getScreenStateLiveData(): LiveData<VacancyDetailsScreenState> = screenStateLiveData
    private var favouriteStatus: Boolean = false

    private val isFavoriteState = MutableLiveData<Boolean>()
    fun isFavorite(): LiveData<Boolean> = isFavoriteState

    init {
        screenStateLiveData.value = VacancyDetailsScreenState.Loading
        getVacancyDetails(vacancyId)
    }

    private fun getVacancyDetails(vacancyId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                interactor.isVacancyFavourite(vacancyId).collect { isVacancyFavourite ->
                    favouriteStatus = isVacancyFavourite
                    isFavoriteState.postValue(isVacancyFavourite)
                }
                interactor.getVacancyDetails(vacancyId, favouriteStatus).collect { detailsResponse ->
                    val code = detailsResponse.resultCode
                    if (code == SUCCESS_CODE && !detailsResponse.vacancyDetail.isNullOrEmpty()) {
                        screenStateLiveData.postValue(VacancyDetailsScreenState.Data(
                            detailsResponse.vacancyDetail[0],
                            favouriteStatus
                        ))
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
            viewModelScope.launch {
                if (favouriteStatus) {
                    deleteFavorite(currentScreenState)
                } else {
                    addFavorite(currentScreenState)
                }
            }
        }
    }

    fun shareVacancy(link: String) {
        interactor.shareVacancy(link)
    }

    private suspend fun deleteFavorite(currentScreenState: VacancyDetailsScreenState.Data) {
        interactor.deleteFavourite(currentScreenState.vacancyDetails).collect { code ->
            when (code) {
                INTERNAL_ERROR_CODE -> {}
                else -> {
                    favouriteStatus = false
                    isFavoriteState.postValue(false)
                }
            }
        }
    }

    private suspend fun addFavorite(currentScreenState: VacancyDetailsScreenState.Data) {
        interactor.addFavourite(currentScreenState.vacancyDetails).collect { code ->
            when (code) {
                INTERNAL_ERROR_CODE -> {}
                else -> {
                    favouriteStatus = true
                    isFavoriteState.postValue(true)
                }
            }
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
        private const val NOT_FOUND_CODE = 404
        const val INTERNAL_ERROR_CODE = 500
    }
}
