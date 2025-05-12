package com.example.course_add_and_drop_manager_app.presentation.components


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AddCourseDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Confirmation") },
        text = { Text("Are you sure you want to add this course?") },
        confirmButton = {
            Button(value = "Yes", onClick = { onConfirm() })
        },
        dismissButton = {
            Button(value = "Yes", onClick = { onConfirm() })
        }
    )
}
