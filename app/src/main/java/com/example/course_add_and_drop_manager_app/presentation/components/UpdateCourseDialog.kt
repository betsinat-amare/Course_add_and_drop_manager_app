package com.example.course_add_and_drop_manager_app.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.course_add_and_drop_manager_app.data.model.Course
import com.example.course_add_and_drop_manager_app.data.model.CourseUpdateRequest

@Composable
fun UpdateCourseDialog(
    course: Course,
    onDismiss: () -> Unit,
    onSubmit: (CourseUpdateRequest) -> Unit
) {
    var title by remember { mutableStateOf(course.title) }
    var code by remember { mutableStateOf(course.code) }
    var description by remember { mutableStateOf(course.description) }
    var creditHours by remember { mutableStateOf(course.credit_hours) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Update Course") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Course Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Course Code") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = creditHours,
                    onValueChange = { creditHours = it.filter { it.isDigit() } },
                    label = { Text("Credit Hours") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            OutlinedButton(
                onClick = {
                    onSubmit(
                        CourseUpdateRequest(
                            title = title,
                            code = code,
                            description = description,
                            credit_hours = creditHours
                        )
                    )
                }
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
