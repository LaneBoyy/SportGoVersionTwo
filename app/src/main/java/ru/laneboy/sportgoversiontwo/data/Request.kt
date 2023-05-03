package ru.laneboy.sportgoversiontwo.data

data class Request(
    val requestId: Int,
    val teamName: String,
    val selectedCompetition: String,
    val teamCaptain: String,
    var requestStatus: RequestStatuses,
    ) {
}