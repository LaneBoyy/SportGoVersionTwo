package ru.laneboy.sportgoversiontwo.presentation

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.laneboy.sportgoversiontwo.data.network.ApiFactory.apiService
import ru.laneboy.sportgoversiontwo.data.network.requests.SignInDataRequest

class SignInViewModel : ViewModel() {

    private val _openParticipantScreen = MutableLiveData<Unit>()
    val openParticipantScreen: LiveData<Unit>
        get() = _openParticipantScreen

    private val _openOrganizerScreen = MutableLiveData<Unit>()
    val openOrganizerScreen: LiveData<Unit>
        get() = _openOrganizerScreen

    fun signIn(inputEmail: String?, inputPassword: String?) {
        val email = inputEmail?.trim() ?: ""
        val password = inputPassword?.trim() ?: ""
        if (email.isEmailValid() && password.isPasswordValid()) {
            viewModelScope.launch(Dispatchers.IO) {
                val signInItem = SignInDataRequest(email, password)
                try {
                    val result = apiService.singIn(signInItem)
                    if (result.isSuccessful) {
                        when (result.body()?.userRole) {
                            PARTICIPANT_ROLE_CODE -> result.body().let {
                                _openParticipantScreen.postValue(Unit)
                            }
                            ORGANIZER_ROLE_CODE -> result.body().let {
                                _openOrganizerScreen.postValue(Unit)
                            }
                        }

                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
//                        Toast.makeText(, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }

    private fun String.isPasswordValid(): Boolean {
        return (this.length >= 2 && this.isNotEmpty())
    }

    companion object {

        private const val PARTICIPANT_ROLE_CODE = "Participant"
        private const val ORGANIZER_ROLE_CODE = "Organizer"
    }
}