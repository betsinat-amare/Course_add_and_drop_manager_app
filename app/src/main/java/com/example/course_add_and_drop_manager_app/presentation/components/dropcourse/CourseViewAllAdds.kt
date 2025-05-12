package com.example.course_add_and_drop_manager_app.presentation.dropcourse



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course_add_and_drop_manager_app.data.local.DataStoreManager
import com.example.course_add_and_drop_manager_app.data.model.AddResponse
import com.example.course_add_and_drop_manager_app.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

@HiltViewModel
class CourseViewModelAllAdds @Inject constructor(
    private val repository: CourseRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    // Backing property for allAdds
    private val _allAdds = mutableStateOf<List<AddResponse>>(emptyList())
    val allAdds: State<List<AddResponse>> = _allAdds

    // Error message (nullable String)
    var errorMessage: String? = null
        private set

    // Fetch all adds from the repository using token from datastore
    fun fetchAllAdds() {
        viewModelScope.launch {
            try {
                val token = dataStoreManager.getToken()
                if (token != null) {
                    val response = repository.getAllAdds(token)
                    _allAdds.value = response
                    errorMessage = null
                } else {
                    errorMessage = "Unauthorized: No token found"
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            }
        }
    }
}
