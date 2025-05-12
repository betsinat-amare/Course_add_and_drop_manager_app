package com.example.course_add_and_drop_manager_app.data.model

data class ProfileResponse(
    val id: Int,
    val full_name: String,
    val username: String,
    val email: String,
    val profile_photo: String?
)
