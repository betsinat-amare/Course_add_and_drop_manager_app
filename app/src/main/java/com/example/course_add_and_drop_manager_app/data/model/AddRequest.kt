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