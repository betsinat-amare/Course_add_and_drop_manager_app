package com.example.course_add_and_drop_manager_app.data.model

data class User(
    val id:String?=null,
    val fullName: String,
    val username: String,
    val password: String,
    val role: String,
    val email: String,
    val profilePhoto: String?
)
