

package com.example.course_add_and_drop_manager_app.data.repository

import com.example.course_add_and_drop_manager_app.data.model.AddCourseRequest
import com.example.course_add_and_drop_manager_app.data.model.AddDropResponse
import com.example.course_add_and_drop_manager_app.data.model.AddResponse
import com.example.course_add_and_drop_manager_app.data.model.Course
import com.example.course_add_and_drop_manager_app.data.model.CourseResponse
import com.example.course_add_and_drop_manager_app.data.model.CourseUpdateRequest

import com.example.course_add_and_drop_manager_app.data.network.RetrofitInstance
import com.example.course_add_and_drop_manager_app.data.network.RetrofitInstance.api

object CourseRepository {
    suspend fun createCourse(course: Course, token: String) {
        RetrofitInstance.api.createCourse("Bearer $token", course)
    }
    suspend fun getAllAdds(token: String): List<AddResponse> {
        return api.getAllAdds("Bearer $token")
    }

    suspend fun deleteCourse(token: String, id: String) = api.deleteCourse(token, id)
    suspend fun updateCourse(token: String, id: String, request: CourseUpdateRequest) =
        api.updateCourse(token, id, request)

    suspend fun addCourse(token: String, request: AddCourseRequest): CourseResponse? {
        return try {
            api.addCourse("Bearer $token", request)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}

