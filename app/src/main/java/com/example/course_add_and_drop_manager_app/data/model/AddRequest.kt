package com.example.course_add_and_drop_manager_app.data.model

data class AddCourseRequest(
    val course_id: String,

    )
data class CourseResponse(
    val id: AddDropResponse?,
    val studentId: Int,
    val courseId: String,
    val approvalStatus: String
)
data class AddResponse(
    val id: Int,
    val student_id: Int,
    val course_id: Int,
    val approval_status: String,
    val added_at: String
)
