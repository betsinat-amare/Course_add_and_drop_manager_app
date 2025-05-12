package com.example.course_add_and_drop_manager_app.data.repository

import com.example.course_add_and_drop_manager_app.data.model.SignUpRequest
import com.example.course_add_and_drop_manager_app.data.network.RetrofitInstance
import com.example.course_add_and_drop_manager_app.data.network.ApiService
class SignupRepository {
    suspend fun signup(signupData: SignUpRequest): Boolean {
        return try {
            val response = RetrofitInstance.api.signUp(signupData)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}
