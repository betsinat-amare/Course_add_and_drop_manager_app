package com.example.course_add_and_drop_manager_app.data.repository

import com.example.course_add_and_drop_manager_app.data.network.ApiService
import com.example.course_add_and_drop_manager_app.data.model.ProfileResponse

class ProfileRepository(private val apiService: ApiService) {

    suspend fun getProfile(token: String): Result<ProfileResponse> {
        return try {
            val response = apiService.getProfile("Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
