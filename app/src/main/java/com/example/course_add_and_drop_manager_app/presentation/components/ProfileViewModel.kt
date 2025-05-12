import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course_add_and_drop_manager_app.data.model.ProfileResponse
import com.example.course_add_and_drop_manager_app.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    var profile by mutableStateOf<ProfileResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchProfile(token: String) {
        viewModelScope.launch {
            isLoading = true
            val result = repository.getProfile(token)
            isLoading = false
            result.onSuccess {
                profile = it
            }.onFailure {
                errorMessage = it.message
            }
        }
    }

}
