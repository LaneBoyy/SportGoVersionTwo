package ru.laneboy.sportgoversiontwo.presentation.add_competition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.laneboy.sportgoversiontwo.data.network.ApiFactory
import ru.laneboy.sportgoversiontwo.data.network.requests.AddCompetitionDataRequest

class AddCompetitionViewModel : ViewModel() {

    private val _competition = MutableLiveData<CompetitionState>()
    val competition: LiveData<CompetitionState>
        get() = _competition

    fun addCompetition(
        inputMatchName: String?,
        inputDescription: String?,
        inputSportType: String?,
        inputDate: String?
    ) {
        val matchName = inputMatchName?.trim() ?: ""
        val description = inputDescription?.trim() ?: ""
        val sportType = inputSportType?.trim() ?: ""
        val date = inputDate?.trim() ?: ""
        if (matchName.isEmpty() || description.isEmpty() || sportType.isEmpty() || date.isEmpty()) {
            _competition.value = CompetitionState.ERROR.apply { error = "Заполните все поля" }
        } else {
            _competition.value = CompetitionState.LOADING
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = ApiFactory.apiService.addCompetition(
                        AddCompetitionDataRequest(
                            date,
                            description,
                            matchName,
                            sportType

                        )
                    )
                    if (result.isSuccessful) {
                        _competition.postValue(CompetitionState.SUCCESS)
                    } else {
                        _competition.postValue(CompetitionState.ERROR.apply {
                            error = result.errorBody().toString()
                        })
                    }
                } catch (e: Exception) {
                    _competition.postValue(CompetitionState.ERROR.apply {
                        error = e.message
                    })
                }
            }
        }
    }

}