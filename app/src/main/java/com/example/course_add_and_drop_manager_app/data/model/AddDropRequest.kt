package com.example.course_add_and_drop_manager_app.data.model

import com.google.gson.annotations.SerializedName

data class AddDropRequest(
    @SerializedName("course_id") val courseId: String
)

data class AddDropResponse(
    val id: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("course_id") val courseId: String,
    @SerializedName("approval_status") val approvalStatus: String
)
