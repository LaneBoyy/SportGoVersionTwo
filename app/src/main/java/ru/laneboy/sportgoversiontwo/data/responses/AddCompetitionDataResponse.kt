package ru.laneboy.sportgoversiontwo.data.responses

data class AddCompetitionDataResponse(
    val competitionName: String,
    val competitionDescription: String,
    val sportType: String,
    val competitionDate: String
)