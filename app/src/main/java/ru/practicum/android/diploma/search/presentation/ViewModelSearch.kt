package ru.practicum.android.diploma.search.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.data.dto.VacanciesRequest
import ru.practicum.android.diploma.search.domain.models.DataConsumer
import ru.practicum.android.diploma.search.presentation.api.InteractorSearchListVacancy
import ru.practicum.android.diploma.search.presentation.models.StateSearchFragment
import ru.practicum.android.diploma.search.presentation.models.VacancyShortUi

class ViewModelSearch(private val interactor: InteractorSearchListVacancy) : ViewModel() {

    private var superJob: Job? = null


    private var _state: MutableLiveData<StateSearchFragment> =
        MutableLiveData(StateSearchFragment.Idle)
    val state: LiveData<StateSearchFragment>
        get() = _state

    fun updateRequest(query: String) {
        loadvacancy(query)
    }

    companion object {

    }

    fun loadvacancy(request: String) {
        viewModelScope.launch {
            interactor.loadVacancy(VacanciesRequest(request)).collect { vacancy ->
                when (vacancy) {
                    is DataConsumer.Success -> {
                        val vacancyUi = vacancy.data.map {
                            VacancyShortUi(
                                id = it.id,
                                title = it.name + ", " + it.city,
                                nameEmployer = it.employer,
                                logoUrl = it.logoUrl,
                                salary = (it.salary ?: "Не указана").toString()
                            )
                        }

                        _state.value = StateSearchFragment.Success(vacancyUi)
                        Log.d("ViewModelSearch", "loadvacancy: ${vacancy.data.size}")
                        vacancyUi.forEach { vacancy ->
                            Log.d("ViewModelSearch", "loadvacancy: ${vacancy}")
                        }
                    }

                    is DataConsumer.ResponseNoContent -> {
                        _state.value = StateSearchFragment.ResponseEmpty
                    }

                    is DataConsumer.ResponseFailure -> {
                        _state.value = StateSearchFragment.ErrorNoInternet
                    }
                }
            }
        }

    }

    fun onVacancyClick(vacancyId: String) {
        //todo
    }


}
