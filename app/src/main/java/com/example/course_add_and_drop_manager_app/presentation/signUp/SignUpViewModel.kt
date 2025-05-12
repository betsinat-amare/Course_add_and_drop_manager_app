







import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course_add_and_drop_manager_app.data.model.SignUpRequest
import com.example.course_add_and_drop_manager_app.data.network.RetrofitInstance
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import kotlinx.coroutines.launch
import java.io.IOException

class SignupViewModel : ViewModel() {

    var id = mutableStateOf("")
    var full_name = mutableStateOf("")
    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var email = mutableStateOf("")
    var role = mutableStateOf("")
    var profile_photo = mutableStateOf<String?>(null)

    var errorMessage = mutableStateOf("")
    var isSignupSuccessful = mutableStateOf(false)

    // Track if the signup button should be enabled
    var isButtonEnabled = mutableStateOf(false)

    // Automatically assigns role based on ID input
    private fun assignRoleBasedOnId() {
        role.value = if (id.value == "2") "Registrar" else "Student"
    }

    // Input validation
    fun validate(): Boolean {
        assignRoleBasedOnId()

        // Check if required fields are filled
        val isValid = when {
            id.value.isBlank() -> {
                errorMessage.value = "ID is required"
                false
            }
            full_name.value.isBlank() -> {
                errorMessage.value = "Full name is required"
                false
            }
            username.value.isBlank() -> {
                errorMessage.value = "Username is required"
                false
            }
            password.value.length < 6 -> {
                errorMessage.value = "Password must be at least 6 characters"
                false
            }
            !email.value.contains("@") -> {
                errorMessage.value = "Invalid email format"
                false
            }
            else -> {
                errorMessage.value = ""
                true
            }
        }

        // Update the button enabled state based on the form validity
        isButtonEnabled.value = isValid

        return isValid
    }

    // Build request object
    private fun buildSignUpRequest(): SignUpRequest {
        return SignUpRequest(
            id = id.value,
            full_name = full_name.value,
            username = username.value,
            password = password.value,
            role = role.value,
            email = email.value,
            profile_photo = profile_photo.value
        )
    }

    // Signup logic
    fun signup(
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        if (!validate()) {
            onError(errorMessage.value)
            return
        }

        val request = buildSignUpRequest()

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.signUp(request)

                if (response.isSuccessful) {
                    isSignupSuccessful.value = true
                    errorMessage.value = ""
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    isSignupSuccessful.value = false
                    errorMessage.value = "Signup failed: ${response.code()} - $errorBody"
                    onError(errorMessage.value)
                }
            } catch (e: IOException) {
                isSignupSuccessful.value = false
                errorMessage.value = "Network error: ${e.localizedMessage}"
                onError(errorMessage.value)
            } catch (e: HttpException) {
                isSignupSuccessful.value = false
                errorMessage.value = "HTTP error: ${e.message}"
                onError(errorMessage.value)
            } catch (e: Exception) {
                isSignupSuccessful.value = false
                errorMessage.value = "Unexpected error: ${e.message}"
                onError(errorMessage.value)
            }
        }
    }

    // Reset form fields
    fun resetForm() {
        id.value = ""
        full_name.value = ""
        username.value = ""
        password.value = ""
        email.value = ""
        role.value = ""
        profile_photo.value = null
        errorMessage.value = ""
        isSignupSuccessful.value = false
    }

    // Utility for testing with static ID
    fun setRoleBasedOnId(sampleId: Int) {
        id.value = sampleId.toString()
        role.value = if (sampleId == 2) "Registrar" else "Student"
    }
}
