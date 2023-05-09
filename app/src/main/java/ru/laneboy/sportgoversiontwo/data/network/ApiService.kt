package ru.laneboy.sportgoversiontwo.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.laneboy.sportgoversiontwo.data.network.requests.AddCompetitionDataRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.SignInDataRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.SignUpDataRequest
import ru.laneboy.sportgoversiontwo.data.network.responses.AuthDataResponse
import ru.laneboy.sportgoversiontwo.data.network.responses.CompetitionItemResponse

interface ApiService {

    //Authentication
    @POST("signin")
    suspend fun singIn(@Body singInData: SignInDataRequest): Response<AuthDataResponse>

    @POST("signup")
    suspend fun signUp(@Body signUpData: SignUpDataRequest): Response<AuthDataResponse>

    //Competition
    @POST("api/competition")
    suspend fun addCompetition(@Body addCompetitionData: AddCompetitionDataRequest)

    @GET("api/competitions")
    suspend fun getCompetitionsList(): Response<List<CompetitionItemResponse>>
}