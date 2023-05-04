package ru.laneboy.sportgoversiontwo.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.laneboy.sportgoversiontwo.data.network.requests.AddCompetitionDataRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.CompetitionRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.SignInDataRequest
import ru.laneboy.sportgoversiontwo.data.network.requests.SignUpDataRequest
import ru.laneboy.sportgoversiontwo.data.network.responses.SignInDataResponse
import ru.laneboy.sportgoversiontwo.data.network.responses.SignUpDataResponse

interface ApiService {

    //Authentication
    @POST("signin")
    suspend fun singIn(@Body singInData: SignInDataRequest): Response<SignInDataResponse>

    @POST("signup")
    suspend fun signUp(@Body signUpData: SignUpDataRequest): Response<SignUpDataResponse>

    //Competition
    @POST("api/competition")
    suspend fun addCompetition(@Body addCompetitionData: AddCompetitionDataRequest)

    @GET("api/competitions")
    suspend fun getCompetitionsList(): List<CompetitionRequest>
}