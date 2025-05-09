package com.example.course_add_and_drop_manager_app.data.model

data class SignUpRequest(
    val id: String,
    val  full_name: String,
    val username: String,
    val password: String,
    val role: String,
    val email: String,
    val profile_photo: String? = null
)

data class SignUpResponse(
    val message: String,
    val user: User
)
