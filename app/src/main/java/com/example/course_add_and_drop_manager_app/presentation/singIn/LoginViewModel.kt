package com.example.course_add_and_drop_manager_app.presentation.singIn

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.course_add_and_drop_manager_app.data.local.DataStoreManager
import com.example.course_add_and_drop_manager_app.data.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var username = mutableStateOf("")
    var password = mutableStateOf("")

    var loginError = mutableStateOf("")
    var loginSuccess = mutableStateOf(false)
    var isButtonEnabled = mutableStateOf(false)

    fun onUsernameChanged(newUsername: String) {
        username.value = newUsername
        updateButtonState()
    }

    fun onPasswordChanged(newPassword: String) {
        password.value = newPassword
        updateButtonState()
    }

    private fun updateButtonState() {
        isButtonEnabled.value = username.value.isNotBlank() && password.value.isNotBlank()
    }

    fun login(
        onAdminSuccess: () -> Unit,
        onUserSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val token = withContext(Dispatchers.IO) {
                    LoginRepository.login(username.value, password.value)
                }

                // ✅ Save token locally
                dataStoreManager.saveToken(token)

                val jwt = JWT(token)
                val userId = jwt.getClaim("id").asInt()
                val role = jwt.getClaim("role").asString()

                Log.d("LoginViewModel", "Token: $token")
                Log.d("LoginViewModel", "userId: $userId, role: $role")

                if (userId == 2 || role?.lowercase() == "registrar") {
                    loginSuccess.value = true
                    onAdminSuccess()
                } else {
                    loginSuccess.value = true
                    onUserSuccess()
                }

            } catch (e: Exception) {
                loginSuccess.value = false
                loginError.value = "Login failed: ${e.message ?: "Unknown error"}"
                onError(loginError.value)
            }
        }
    }
}





//package com.example.course_add_and_drop_manager_app.presentation.singIn
//
//import android.util.Log
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.auth0.android.jwt.JWT
//import com.example.course_add_and_drop_manager_app.data.repository.LoginRepository
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class LoginViewModel : ViewModel() {
//
//    var username = mutableStateOf("")
//    var password = mutableStateOf("")
//
//    var loginError = mutableStateOf("")
//    var loginSuccess = mutableStateOf(false)
//    var isButtonEnabled = mutableStateOf(false)
//
//    fun onUsernameChanged(newUsername: String) {
//        username.value = newUsername
//        updateButtonState()
//    }
//
//    fun onPasswordChanged(newPassword: String) {
//        password.value = newPassword
//        updateButtonState()
//    }
//
//    private fun updateButtonState() {
//        isButtonEnabled.value = username.value.isNotBlank() && password.value.isNotBlank()
//    }
//
//    fun login(
//        onAdminSuccess: () -> Unit,
//        onUserSuccess: () -> Unit,
//        onError: (String) -> Unit
//    ) {
//        viewModelScope.launch {
//            try {
//
//                val token = withContext(Dispatchers.IO) {
//
//                    LoginRepository.login(username.value, password.value)
//                }
//
//                // Process the token asynchronously
//                val jwt = JWT(token)
//                val userId = jwt.getClaim("id").asInt()
//                val role = jwt.getClaim("role").asString()
//
//                Log.d("LoginViewModel", "Token: $token")
//                Log.d("LoginViewModel", "userId: $userId, role: $role")
//
//                // Route based on userId or role
//                if (userId == 2 || role?.lowercase() == "registrar") {
//                    loginSuccess.value = true
//                    onAdminSuccess()
//                } else {
//                    loginSuccess.value = true
//                    onUserSuccess()
//                }
//
//            } catch (e: Exception) {
//                loginSuccess.value = false
//                loginError.value = "Login failed: ${e.message ?: "Unknown error"}"
//                onError(loginError.value)
//            }
//        }
//    }
//
//}