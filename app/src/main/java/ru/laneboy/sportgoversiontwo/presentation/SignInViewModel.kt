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
                val signInItem = SignInDataRequest(email, password)
                try {
                    val result = apiService.singIn(signInItem)
                    if (result.isSuccessful) {
                        result.body()?.let {
                            _openNextScreen.postValue(Unit)
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

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }
}