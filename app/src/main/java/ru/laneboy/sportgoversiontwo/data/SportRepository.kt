package ru.laneboy.sportgoversiontwo.data

import ru.laneboy.sportgoversiontwo.data.network.wrapper.ApiErrorResponse
import ru.laneboy.sportgoversiontwo.data.network.wrapper.ApiSuccessResponse
import ru.laneboy.sportgoversiontwo.data.network.ApiFactory
import ru.laneboy.sportgoversiontwo.data.network.requests.AddCompetitionDataRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.AddRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.ChangeStatusRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.SignInDataRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.SignUpDataRequest
import ru.laneboy.sportgoversiontwo.data.network.responses.CompetitionItemResponse
import ru.laneboy.sportgoversiontwo.data.network.responses.UserRole
import ru.laneboy.sportgoversiontwo.domain.AuthData
import ru.laneboy.sportgoversiontwo.domain.RequestItem
import ru.laneboy.sportgoversiontwo.domain.Resource

object SportRepository {

    private val apiService = ApiFactory.apiService

    lateinit var authData: AuthData

    val userId: Int
        get() = authData.userId

    val isUser: Boolean
        get() = authData.userRole == UserRole.PARTICIPANT

    suspend fun signIn(email: String, password: String): Resource<AuthData> {
        val signIn = SignInDataRequest(email, password)
        return when (val t = apiService.singIn(signIn)) {
            is ApiSuccessResponse -> {
                authData = t.data.toAuthData()
                Resource.success(t.data.toAuthData())
            }

            is ApiErrorResponse -> Resource.error(t.error)
        }
    }

    suspend fun signUp(
        email: String,
        nickname: String,
        password: String,
        role: Int
    ): Resource<AuthData> {
        val signUp = SignUpDataRequest(email, nickname, password, role)
        return when (val t = apiService.signUp(signUp)) {
            is ApiSuccessResponse -> {
                authData = t.data.toAuthData()
                Resource.success(t.data.toAuthData())
            }

            is ApiErrorResponse -> Resource.error(t.error)
        }
    }

    suspend fun addCompetition(
        matchName: String,
        description: String,
        sportType: String,
        date: String
    ): Resource<Any> {
        val competition = AddCompetitionDataRequest(
            date,
            description,
            matchName,
            sportType
        )
        return when (val t = apiService.addCompetition(competition)) {
            is ApiSuccessResponse -> Resource.success(t.data)
            is ApiErrorResponse -> Resource.error(t.error)
        }
    }

    suspend fun getCompetitionList(): Resource<List<CompetitionItemResponse>> {
        return when (val t = apiService.getCompetitionsList()) {
            is ApiSuccessResponse -> Resource.success(t.data)
            is ApiErrorResponse -> Resource.error(t.error)
        }
    }

    suspend fun getRequestsList(): Resource<List<RequestItem>> {
        return when (val t = apiService.getRequestList(userId)) {
            is ApiSuccessResponse -> Resource.success(t.data.map { it.toRequestItem() })
            is ApiErrorResponse -> Resource.error(t.error)
        }
    }

    suspend fun getCompetitionRequests(id: Int): Resource<List<RequestItem>> {
        return when (val t = apiService.getCompetitionRequests(id)) {
            is ApiSuccessResponse -> Resource.success(t.data.map { it.toRequestItem() })
            is ApiErrorResponse -> Resource.error(t.error)
        }
    }

    suspend fun changeRequestStatus(requestId: Int, status: Int): Resource<Any> {
        return when (val t =
            apiService.changeRequestStatus(ChangeStatusRequest(requestId, status))) {
            is ApiSuccessResponse -> Resource.success(t.data)
            is ApiErrorResponse -> Resource.error(t.error)
        }
    }

    suspend fun addRequest(
        competitionId: Int,
        teamName: String,
        teamCaptain: String
    ): Resource<RequestItem> {
        val request = AddRequest(userId, competitionId, teamName, teamCaptain)
        return when (val t = apiService.addRequest(request)) {
            is ApiSuccessResponse -> Resource.success(t.data.toRequestItem())
            is ApiErrorResponse -> Resource.error(t.error)
        }
    }
}