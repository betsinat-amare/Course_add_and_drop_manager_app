package com.example.course_add_and_drop_manager_app.presentation.singIn

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course_add_and_drop_manager_app.data.model.LoginRequest
import com.example.course_add_and_drop_manager_app.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    // States for username and password input fields
    var username = mutableStateOf("")
    var password = mutableStateOf("")

    // State for login success or error messages
    var loginError = mutableStateOf("")
    var loginSuccess = mutableStateOf(false)

    // State for controlling button enabled/disabled
    var isButtonEnabled = mutableStateOf(false)

    // Check if the login button should be enabled
    private fun updateButtonState() {
        isButtonEnabled.value = username.value.isNotBlank() && password.value.isNotBlank()
    }

    // Function to handle login attempt
    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Perform login request with Retrofit
                val response = RetrofitInstance.api.login(
                    LoginRequest(username.value, password.value)
                )

                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token

                    // Save token to secure storage or local storage if necessary

                    loginSuccess.value = true
                    onSuccess()
                } else {
                    loginError.value = "Invalid credentials"
                    onError("Invalid username or password")
                }
            } catch (e: Exception) {
                loginError.value = "Network error"
                onError("Error: ${e.localizedMessage}")
            }
        }
    }

    // Listen for changes to username or password and update button state accordingly
    fun onUsernameChanged(newUsername: String) {
        username.value = newUsername
        updateButtonState()
    }

    fun onPasswordChanged(newPassword: String) {
        password.value = newPassword
        updateButtonState()
    }
}
