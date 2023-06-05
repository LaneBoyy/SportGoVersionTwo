package ru.laneboy.sportgoversiontwo.data.network

import ru.laneboy.sportgoversiontwo.data.network.wrapper.ApiResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import ru.laneboy.sportgoversiontwo.data.network.requests.AddCompetitionDataRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.AddRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.ChangeStatusRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.SignInDataRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.SignUpDataRequest
import ru.laneboy.sportgoversiontwo.data.network.responses.AuthDataResponse
import ru.laneboy.sportgoversiontwo.data.network.responses.CompetitionItemResponse
import ru.laneboy.sportgoversiontwo.data.network.responses.RequestItemResponse

interface ApiService {

    //Authentication
    @POST("signin")
    suspend fun singIn(@Body singInData: SignInDataRequest): ApiResponse<AuthDataResponse>

    @POST("signup")
    suspend fun signUp(@Body signUpData: SignUpDataRequest): ApiResponse<AuthDataResponse>

    //Competition
    @POST("api/competition")
    suspend fun addCompetition(@Body addCompetitionData: AddCompetitionDataRequest): ApiResponse<Any>

    @GET("api/competitions")
    suspend fun getCompetitionsList(): ApiResponse<List<CompetitionItemResponse>>

    @GET("api/user/{user_id}/requests")
    suspend fun getRequestList(
        @Path("user_id") userId: Int
    ): ApiResponse<List<RequestItemResponse>>

    @GET("api/competition/{competition_id}/requests")
    suspend fun getCompetitionRequests(
        @Path("competition_id") competitionId: Int
    ): ApiResponse<List<RequestItemResponse>>

    @PATCH("api/request")
    suspend fun changeRequestStatus(
        @Body changeStatusRequest: ChangeStatusRequest
    ): ApiResponse<Any>

    @POST("api/request")
    suspend fun addRequest(
        @Body request: AddRequest
    ): ApiResponse<RequestItemResponse>
}