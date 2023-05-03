package ru.laneboy.sportgoversiontwo.data.responses

import com.google.gson.annotations.SerializedName
import java.util.*

data class CompetitionsResponse(
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