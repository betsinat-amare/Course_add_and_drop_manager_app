package com.example.course_add_and_drop_manager_app.data.network

import com.example.course_add_and_drop_manager_app.data.model.Course
import com.example.course_add_and_drop_manager_app.data.model.LoginRequest
import com.example.course_add_and_drop_manager_app.data.model.LoginResponse
import com.example.course_add_and_drop_manager_app.data.model.SignUpRequest
import com.example.course_add_and_drop_manager_app.data.model.SignUpResponse
import com.example.course_add_and_drop_manager_app.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // User sign-up endpoint
    @POST("auth/signup")  // Adjust path based on your backend
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    // Fetch all courses
    @GET("courses")  // Removed leading slash for consistency
    suspend fun getCourses(): Response<List<Course>>  // Now returns Response for error handling

    // User login endpoint
//    @POST("login")
//    suspend fun login(@Body loginRequest: LoginRequest): Response<User>  // Also wrapped in Response
}
