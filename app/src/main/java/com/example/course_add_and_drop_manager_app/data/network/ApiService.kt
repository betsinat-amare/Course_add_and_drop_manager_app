package com.example.course_add_and_drop_manager_app.data.network

import com.example.course_add_and_drop_manager_app.data.model.Course
import com.example.course_add_and_drop_manager_app.data.model.LoginRequest
import com.example.course_add_and_drop_manager_app.data.model.LoginResponse
import com.example.course_add_and_drop_manager_app.data.model.SignUpRequest
import com.example.course_add_and_drop_manager_app.data.model.SignUpResponse
import com.example.course_add_and_drop_manager_app.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // User sign-up endpoint
    @POST("auth/signup")  // Adjust path based on your backend
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    // Fetch all courses
    @GET("/courses")
    suspend fun getCourses(): List<Course>

    @GET("/courses/{id}")
    suspend fun getCourse(@Path("id") id: Int): Course

    @POST("courses")
    suspend fun createCourse(
        @Header("Authorization") token: String,
        @Body course: Course
    ): Response<Course>

    @PUT("/courses/{id}")
    suspend fun updateCourse(@Path("id") id: Int, @Body course: Course): Response<Unit>

    @DELETE("/courses/{id}")
    suspend fun deleteCourse(@Path("id") id: Int): Response<Unit>


}

