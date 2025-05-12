package com.example.course_add_and_drop_manager_app.presentation.dropcourse

import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course_add_and_drop_manager_app.data.local.DataStoreManager
import com.example.course_add_and_drop_manager_app.data.model.AddDropRequest
import com.example.course_add_and_drop_manager_app.data.model.Course
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

                val response = CourseRepository.addCourse(courseId)

                if (response.isSuccessful) {
                    snackbarMessage = "Course request sent for approval."
                } else {
                    snackbarMessage = "Failed to add course: ${response.message()}"
                }
            } catch (e: Exception) {
                snackbarMessage = "Something went wrong: ${e.localizedMessage}"
            }
        }
    }




}
@Composable
fun SnackbarObserver(viewModel: CourseViewModel) {
    val message = viewModel.snackbarMessage
    if (message != null) {
        // Display the snackbar
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