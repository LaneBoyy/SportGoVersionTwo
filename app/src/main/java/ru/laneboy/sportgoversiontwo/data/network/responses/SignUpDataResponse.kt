package ru.laneboy.sportgoversiontwo.data.network.responses

import com.google.gson.annotations.SerializedName

data class SignUpDataResponse(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("traceId")
    val traceId: String?,
    @SerializedName("type")
    val type: String?
)