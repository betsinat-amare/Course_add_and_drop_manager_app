package com.example.course_add_and_drop_manager_app.data.model

data class AddRequest(
    val id: Int,
    val studentId: Int,
    val courseId: Int,
    val approvalStatus: String,
    val addedAt: String
)
