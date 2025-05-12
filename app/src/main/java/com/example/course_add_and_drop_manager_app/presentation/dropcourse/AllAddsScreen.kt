import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.course_add_and_drop_manager_app.presentation.dropcourse.CourseViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.course_add_and_drop_manager_app.presentation.dropcourse.CourseViewModelAllAdds
import androidx.hilt.navigation.compose.hiltViewModel
@Composable
fun AllAddsScreen(viewModel: CourseViewModelAllAdds = hiltViewModel()) {
    val adds = viewModel.allAdds.value
    val error = viewModel.errorMessage

    LaunchedEffect(Unit) {
        viewModel.fetchAllAdds()
    }

    when {
        error != null && error.isNotEmpty() -> {
            Text("Error: $error", color = Color.Red)
        }

        adds.isNotEmpty() -> {
            LazyColumn {
                items(adds) { add ->
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Add ID: ${add.id}")
                        Text("Student ID: ${add.student_id}")
                        Text("Course ID: ${add.course_id}")
                        Text("Approval Status: ${add.approval_status}")
                        Text("Added At: ${add.added_at}")
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }

        else -> {
            CircularProgressIndicator()
        }
    }
}

