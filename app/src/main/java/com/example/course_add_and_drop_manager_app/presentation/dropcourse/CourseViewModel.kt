package com.example.course_add_and_drop_manager_app.presentation.dropcourse

import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course_add_and_drop_manager_app.data.local.DataStoreManager
import com.example.course_add_and_drop_manager_app.data.model.AddCourseRequest
import com.example.course_add_and_drop_manager_app.data.model.AddResponse
import com.example.course_add_and_drop_manager_app.data.model.Course
import com.example.course_add_and_drop_manager_app.data.model.CourseResponse
import com.example.course_add_and_drop_manager_app.data.model.CourseUpdateRequest
import com.example.course_add_and_drop_manager_app.data.network.RetrofitInstance.api
import com.example.course_add_and_drop_manager_app.data.repository.CourseRepository
import kotlinx.coroutines.launch

class CourseViewModel(
    private val dataStoreManager: DataStoreManager,
    private val repository: CourseRepository
) : ViewModel() {
    var snackbarMessage by mutableStateOf<String?>(null)
    var courses by mutableStateOf<List<Course>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
    val title = mutableStateOf("")
    val code = mutableStateOf("")
    val description = mutableStateOf("")
    val creditHours = mutableStateOf("")



    fun createCourse(token: String) {
        viewModelScope.launch {
            try {
                val request = Course(
                    title = title.value,
                    code = code.value,
                    description = description.value,
                    credit_hours = (creditHours.value.toIntOrNull() ?: 0).toString()
                )
                CourseRepository.createCourse(request, token)
                successMessage = "Course created successfully!"
                errorMessage = ""
            } catch (e: Exception) {
                errorMessage = "Failed to create course: ${e.message}"
                successMessage = ""
            }
        }
    }

    init {
        fetchCourses()
    }

    fun fetchCourses() {
        viewModelScope.launch {
            try {
                val token = dataStoreManager.getToken()
                if (token != null) {
                    val result = api.getCourses("Bearer $token")
                    courses = result
                } else {
                    errorMessage = "No token found."
                }
            } catch (e: Exception) {
                errorMessage = "Failed to load courses: ${e.message}"
            }
        }
    }
    var successMessage by mutableStateOf<String?>(null)
    fun deleteCourse(id: String) {
        viewModelScope.launch {
            try {
                val token = dataStoreManager.getToken() ?: return@launch
                repository.deleteCourse("Bearer $token", id)
                fetchCourses()
            } catch (e: Exception) {
                errorMessage = "Failed to delete course: ${e.message}"
            }
        }
    }

    fun updateCourse(id: String, updated: CourseUpdateRequest) {
        viewModelScope.launch {
            try {
                val token = dataStoreManager.getToken() ?: return@launch
                repository.updateCourse("Bearer $token", id, updated)
                fetchCourses()
            } catch (e: Exception) {
                errorMessage = "Failed to update course: ${e.message}"
            }
        }
    }
    fun addCourse(courseId: String) {
        viewModelScope.launch {
            try {
                val token = dataStoreManager.getToken()
                if (token.isNullOrEmpty()) {
                    snackbarMessage = "Unauthorized. Login required."
                    return@launch
                }

                val request = AddCourseRequest(course_id = courseId)
                val response = repository.addCourse(token, request)

                if (response != null) {
                    snackbarMessage = "Course request for ${response.courseId} sent for approval. Status: ${response.approvalStatus}"
                    // Optional: Refresh data or add to UI
                } else {
                    snackbarMessage = "Failed to send course request"
                }

            } catch (e: Exception) {
                snackbarMessage = "Error: ${e.localizedMessage}"
            }
        }
    }
    val allAdds = mutableStateOf<List<AddResponse>>(emptyList())
//    val errorMessage = mutableStateOf("")

    fun fetchAllAdds() {
        viewModelScope.launch {
            try {
                val token = dataStoreManager.getToken()
                if (token != null) {
                    val response = repository.getAllAdds(token)
                    allAdds.value = response
                } else {
                    errorMessage = "Unauthorized: No token"
                }
            } catch (e: Exception) {
                errorMessage= e.message ?: "Unknown error"
            }
        }
    }







}
@Composable
fun SnackbarObserver(viewModel: CourseViewModel) {
    val message = viewModel.snackbarMessage
    if (message != null) {
        LaunchedEffect(message) {
            // You can auto-dismiss the snackbar after 3 seconds (optional)
            kotlinx.coroutines.delay(3000)
            viewModel.snackbarMessage = null
        }

        Snackbar(
            action = {
                Button(onClick = { viewModel.snackbarMessage = null }) {
                    Text("Dismiss")
                }
            }
        ) {
            Text(message)
        }
    }
}
