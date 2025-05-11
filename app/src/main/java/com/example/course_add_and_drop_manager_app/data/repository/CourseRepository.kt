//package com.example.course_add_and_drop_manager_app.data.repository
//
//import com.example.course_add_and_drop_manager_app.data.model.Course
//import com.example.course_add_and_drop_manager_app.data.network.ApiService
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//class CourseRepository(private val apiService: ApiService) {
//
//    suspend fun createCourse(course: Course, token: String): Result<Unit> {
//        return withContext(Dispatchers.IO) {
//            try {
//                // Include the token in the Authorization header
//                val response = apiService.createCourse(course, "Bearer $token")
//
//                if (response.isSuccessful) {
//                    Result.success(Unit)
//                } else {
//                    Result.failure(Exception("Failed to add course: ${response.message()}"))
//                }
//            } catch (e: Exception) {
//                Result.failure(e)
//            }
//        }
//    }
//}

package com.example.course_add_and_drop_manager_app.data.repository

import com.example.course_add_and_drop_manager_app.data.model.Course

import com.example.course_add_and_drop_manager_app.data.network.RetrofitInstance

object CourseRepository {
    suspend fun createCourse(course: Course, token: String) {
        RetrofitInstance.api.createCourse("Bearer $token", course)
    }
}

