package ru.laneboy.sportgoversiontwo.presentation.organizer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import okio.BufferedSource
import retrofit2.Response
import ru.laneboy.sportgoversiontwo.data.network.ApiFactory.apiService
import ru.laneboy.sportgoversiontwo.data.network.responses.CompetitionItemResponse

class MatchListOrganizerViewModel : ViewModel() {

    private val _organizerList = MutableLiveData<Response<List<CompetitionItemResponse>>>()
    val organizerList: LiveData<Response<List<CompetitionItemResponse>>>
        get() = _organizerList


    fun loadCompetitionList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _organizerList.postValue(apiService.getCompetitionsList())
            } catch (e: Exception) {
                _organizerList.postValue(Response.error(600, "Unknown error".toResponseBody()))
            }
        }
    }
}