package ru.laneboy.sportgoversiontwo.presentation

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.laneboy.sportgoversiontwo.data.network.ApiFactory.apiService
import ru.laneboy.sportgoversiontwo.data.network.requests.SignUpDataRequest

class SignUpViewModel: ViewModel() {

    private val _openFirstScreen = MutableLiveData<Unit>()
    val openFirstScreen: LiveData<Unit>
        get() = _openFirstScreen

    private val _openSecondScreen = MutableLiveData<Unit>()
    val openSecondScreen: LiveData<Unit>
        get() = _openSecondScreen

    fun signUp(inputEmail: String?, inputPassword: String?, inputRole: Int) {
        val email = inputEmail?.trim() ?: ""
        val password = inputPassword?.trim() ?: ""
        if (email.isEmailValid() && password.isPasswordValid()) {
            viewModelScope.launch(Dispatchers.IO) {
                val signUpItem = SignUpDataRequest(email, null, password, inputRole)
                try {
                    val result = apiService.signUp(signUpItem)
                    if (result.isSuccessful) {
                        when (inputRole) {
                            0 -> result.body()?.let {
                                _openFirstScreen.postValue(Unit)
                            }
                            1 -> result.body()?.let {
                                _openSecondScreen.postValue(Unit)
                            }
                        }
                    }
                } catch (e: Exception) {
                    TODO()
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
}