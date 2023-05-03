package ru.laneboy.sportgoversiontwo.data

data class Game(
    val gameId: Int,
    val firstTeamName: String,
    val secondTeamName: String,
    val firstTeamScore: Int,
    val secondTeamScore: Int,
    val nameOfMainCompetition: String,
) {
}