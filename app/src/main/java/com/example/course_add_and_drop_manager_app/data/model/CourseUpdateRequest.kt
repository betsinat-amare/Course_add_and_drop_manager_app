package com.example.course_add_and_drop_manager_app.data.model

data class CourseUpdateRequest(
    val title: String,
    val code: String,
    val description: String,
    val credit_hours: String
)
