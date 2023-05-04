package ru.laneboy.sportgoversiontwo.presentation

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.laneboy.sportgoversiontwo.data.network.ApiFactory.apiService
import ru.laneboy.sportgoversiontwo.data.network.responses.SignInDataResponse

class SignInViewModel : ViewModel() {

//    private val _errorInputCount = MutableLiveData<Boolean>()
//    val errorInputCount: LiveData<Boolean>
//        get() = _errorInputCount

    private val _openNextScreen = MutableLiveData<Unit>()
    val openNextScreen: LiveData<Unit>
        get() = _openNextScreen

    fun signIn(inputEmail: String?, inputPassword: String?) {
        val email = inputEmail?.trim() ?: ""
        val password = inputPassword?.trim() ?: ""
        if (email.isEmailValid() && password.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val signInItem = SignInDataResponse(email, password)
                try {
                    apiService.singIn(signInItem)
                    _openNextScreen.postValue(Unit)
                } catch (e: Exception) {

                }
            }
        }
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }
}