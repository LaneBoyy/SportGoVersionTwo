package ru.laneboy.sportgoversiontwo.presentation.sign_up

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.laneboy.sportgoversiontwo.data.network.ApiFactory.apiService
import ru.laneboy.sportgoversiontwo.data.network.requests.SignUpDataRequest

class SignUpViewModel : ViewModel() {

    private val _openOrganizerScreen = MutableLiveData<UserRole>()
    val openOrganizerScreen: LiveData<UserRole>
        get() = _openOrganizerScreen

    fun signUp(inputEmail: String?, inputPassword: String?, inputRole: Int) {
        val email = inputEmail?.trim() ?: ""
        val password = inputPassword?.trim() ?: ""
        if (email.isEmailValid() && password.isPasswordValid()) {
            viewModelScope.launch(Dispatchers.IO) {
                _openOrganizerScreen.postValue(UserRole.LOADING)
                val signUpItem = SignUpDataRequest(email, "", password, inputRole)
                try {
                    val result = apiService.signUp(signUpItem)
                    if (result.isSuccessful) {
                        when (result.body()?.userRole) {
                            PARTICIPANT_ROLE_CODE -> _openOrganizerScreen.postValue(UserRole.PARTICIPANT)
                            ORGANIZER_ROLE_CODE -> _openOrganizerScreen.postValue(UserRole.ORGANIZER)
                        }
                    } else {
                        _openOrganizerScreen.postValue(UserRole.ERROR.apply {
                            error = ERROR_STRING
                        })
                    }
                } catch (e: Exception) {
                    _openOrganizerScreen.postValue(UserRole.ERROR.apply {
                        error = ERROR_NOT_INTERNET_STRING
                    })
                }
            }
        } else {
            _openOrganizerScreen.postValue(UserRole.ERROR.apply {
                error = ERROR_INCORRECT_STRING
            })
        }
    }

    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }

    private fun String.isPasswordValid(): Boolean {
        return (this.length >= 2)
    }

    companion object {

        private const val PARTICIPANT_ROLE_CODE = "Participant"
        private const val ORGANIZER_ROLE_CODE = "Organizer"
        private const val ERROR_STRING = "Неверная почта или пароль"
        private const val ERROR_INCORRECT_STRING = "Некорректно введена почта или пароль"
        private const val ERROR_NOT_INTERNET_STRING =
            "Отсутствует подключение к интернету. Проверьте соединение и попробуйте снова"
    }
}