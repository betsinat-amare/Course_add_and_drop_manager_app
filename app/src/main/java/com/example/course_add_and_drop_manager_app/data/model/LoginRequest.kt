package com.example.course_add_and_drop_manager_app.data.model

data class LoginRequest(
    val username: String,
    val password: String
)
data class LoginResponse(
    val token: String
)