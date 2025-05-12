package com.example.course_add_and_drop_manager_app.data.model

data class DropRequest(
    val id: Int,
    val addId: Int,
    val studentId: Int,
    val courseId: Int,
    val approvalStatus: String,
    val droppedAt: String
)
