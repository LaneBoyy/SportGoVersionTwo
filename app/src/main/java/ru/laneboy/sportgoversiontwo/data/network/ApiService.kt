package ru.laneboy.sportgoversiontwo.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.laneboy.sportgoversiontwo.data.responses.AddCompetitionDataResponse
import ru.laneboy.sportgoversiontwo.data.responses.CompetitionsResponse
import ru.laneboy.sportgoversiontwo.data.responses.SignInDataResponse
import ru.laneboy.sportgoversiontwo.data.responses.SignUpDataResponse

interface ApiService {

    //Authentication
    @POST("signin")
    suspend fun singIn(@Body singInData: SignInDataResponse)

    @POST("signup")
    suspend fun signUp(@Body signUpData: SignUpDataResponse)

    //Competition
    @POST("api/competition")
    suspend fun addCompetition(@Body addCompetitionData: AddCompetitionDataResponse)

    @GET("api/competitions")
    suspend fun getCompetitionsList(): List<CompetitionsResponse>
}