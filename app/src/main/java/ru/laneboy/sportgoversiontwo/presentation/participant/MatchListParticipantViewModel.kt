package ru.laneboy.sportgoversiontwo.presentation.participant

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

class MatchListParticipantViewModel : ViewModel() {

    private val _participantList = MutableLiveData<Response<List<CompetitionItemResponse>>>()
    val participantList: LiveData<Response<List<CompetitionItemResponse>>>
        get() = _participantList


    fun loadCompetitionList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _participantList.postValue(apiService.getCompetitionsList())
            } catch (e: Exception) {
                _participantList.postValue(Response.error(600, "Unknown error".toResponseBody()))
            }
        }
    }
}