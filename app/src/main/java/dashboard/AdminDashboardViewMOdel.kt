//package com.example.course_add_and_drop_manager_app.presentation.dashboard
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//import com.example.course_add_and_drop_manager_app.data.model.Course
//import com.example.course_add_and_drop_manager_app.data.repository.CourseRepository
//import android.content.Context
//import android.content.SharedPreferences
//
//class AdminDashboardViewModel(
//    private val repository: CourseRepository,
//    private val context: Context  // Context is needed to access SharedPreferences
//) : ViewModel() {
//
//    var title by mutableStateOf("")
//    var code by mutableStateOf("")
//    var description by mutableStateOf("")
//    var credit_hours by mutableStateOf("")
//    var message by mutableStateOf("")
//    var isLoading by mutableStateOf(false)
//    var courseAdded by mutableStateOf(false)
//        private set
//
//    // SharedPreferences instance for retrieving the token
//    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
//
//    fun addCourse(token: String) {
//        viewModelScope.launch {
//            isLoading = true
//            courseAdded = false  // reset before adding
//            try {
//                val creditHoursInt = credit_hours.toIntOrNull()
//                if (creditHoursInt == null) {
//                    message = "Credit hours must be a valid number."
//                    return@launch
//                }
//
//                val course = Course(
//                    title = title,
//                    code = code,
//                    description = description,
//                    credit_hours = credit_hours
//                )
//
//                // Retrieve the token from SharedPreferences
//                val token = sharedPreferences.getString("auth_token", null)
//
//                if (token.isNullOrEmpty()) {
//                    message = "User not authenticated. Please log in again."
//                    return@launch
//                }
//
//                // Pass the token to the repository to make the request
//                val result = repository.createCourse(course, token)
//
//                result.onSuccess {
//                    message = "Course added!"
//                    clearFields()
//                    courseAdded = true  // ✅ trigger success
//                }.onFailure {
//                    message = "Failed to add course: ${it.localizedMessage}"
//                }
//            } catch (e: Exception) {
//                message = "Exception: ${e.localizedMessage}"
//            } finally {
//                isLoading = false
//            }
//        }
//    }
//
//    private fun clearFields() {
//        title = ""
//        code = ""
//        description = ""
//        credit_hours = ""
//    }
//}
package com.example.course_add_and_drop_manager_app.presentation.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course_add_and_drop_manager_app.data.model.Course
import com.example.course_add_and_drop_manager_app.data.repository.CourseRepository
import kotlinx.coroutines.launch

class CourseViewModel : ViewModel() {

    val title = mutableStateOf("")
    val code = mutableStateOf("")
    val description = mutableStateOf("")
    val creditHours = mutableStateOf("")

    val errorMessage = mutableStateOf("")
    val successMessage = mutableStateOf("")

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
                successMessage.value = "Course created successfully!"
                errorMessage.value = ""
            } catch (e: Exception) {
                errorMessage.value = "Failed to create course: ${e.message}"
                successMessage.value = ""
            }
        }
    }
}