package ru.laneboy.sportgoversiontwo.data

data class Competition(
    val competitionId: Int,
    val competitionName: String,
    val competitionDescription: String,
    val sportType: String,
    val competitionDate: String,
    var countOfPlayers: Int,
) {
}