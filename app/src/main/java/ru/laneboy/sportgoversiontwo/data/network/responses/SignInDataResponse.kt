package ru.laneboy.sportgoversiontwo.data.network.responses

import com.google.gson.annotations.SerializedName

data class SignInDataResponse(
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("userName")
    val userName: String?,
    @SerializedName("userEmail")
    val userEmail: String?,
    @SerializedName("userPassword")
    val userPassword: String?,
    @SerializedName("userRole")
    val userRole: String?
)
