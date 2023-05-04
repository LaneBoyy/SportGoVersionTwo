package ru.laneboy.sportgoversiontwo.data.network.requests

import com.google.gson.annotations.SerializedName
import java.util.*

data class CompetitionRequest(
    @SerializedName("competitionId")
    val competitionId: Int?,
    @SerializedName("competitionName")
    val competitionName: String?,
    @SerializedName("competitionDescription")
    val competitionDescription: String?,
    @SerializedName("sportType")
    val sportType: String?,
    @SerializedName("competitionDate")
    val competitionDate: String?,
    @SerializedName("countOfPlayers")
    val countOfPlayers: Int?
)