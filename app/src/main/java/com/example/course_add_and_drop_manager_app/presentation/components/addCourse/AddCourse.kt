package com.example.course_add_and_drop_manager_app.presentation.components.addCourse


import ProfileImgPlaceholder
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.course_add_and_drop_manager_app.Course_add_and_drop_managerAppRoute
import com.example.course_add_and_drop_manager_app.Screen
import com.example.course_add_and_drop_manager_app.data.local.DataStoreManager
import com.example.course_add_and_drop_manager_app.data.model.Course
import com.example.course_add_and_drop_manager_app.data.repository.CourseRepository
import com.example.course_add_and_drop_manager_app.presentation.components.*
import com.example.course_add_and_drop_manager_app.presentation.dropcourse.CourseViewModel
import com.example.course_add_and_drop_manager_app.ui.theme.colorGrayBackground
import kotlinx.coroutines.launch

@Composable
fun AddCourse() {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }

    val viewModel = remember {
        CourseViewModel(
            dataStoreManager = dataStoreManager,
            repository = CourseRepository
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }
    var query by remember { mutableStateOf("") }

    var showUpdateDialog by remember { mutableStateOf(false) }
    var selectedCourse by remember { mutableStateOf<Course?>(null) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var courseToDelete by remember { mutableStateOf<Course?>(null) }

    var showAddDialog by remember { mutableStateOf(false) }
    var courseToAdd by remember { mutableStateOf<Course?>(null) }

    val onBack: () -> Unit = {
        Course_add_and_drop_managerAppRoute.navigateTo(Screen.UserDashboardScreen)
    }

    SystemBackButtonHandler { onBack() }

    // Scaffold for Snackbar and content
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = colorGrayBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = { onBack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = "Add Course",
                            modifier = Modifier.padding(top = 17.dp),
                            style = TextStyle(fontSize = 18.sp)
                        )
                        ProfileImgPlaceholder()
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    SearchBar(
                        query = query,
                        onQueryChange = { query = it }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    viewModel.errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    viewModel.courses
                        .filter { it.title.contains(query, ignoreCase = true) }
                        .forEach { course ->
                            AddCard(
                                onAddClick = {
                                    courseToAdd = course
                                    showAddDialog = true
                                },
                                header = course.title,
                                text1 = course.description,
                                text2 = "Credit Hours: ${course.credit_hours}",
                                actions = {
                                    Row {
                                        IconButton(onClick = {
                                            courseToDelete = course
                                            showDeleteDialog = true
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = Color.Red
                                            )
                                        }
                                        IconButton(onClick = {
                                            selectedCourse = course
                                            showUpdateDialog = true
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Update",
                                                tint = Color(0xFF3F51B5)
                                            )
                                        }
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                Footer(
                    currentScreen = Course_add_and_drop_managerAppRoute.currentScreen.value,
                    onItemSelected = { selectedScreen ->
                        Course_add_and_drop_managerAppRoute.navigateTo(selectedScreen)
                    },
                )
            }

            // Dialogs stay outside the scrollable column
            if (showAddDialog && courseToAdd != null) {
                AlertDialog(
                    onDismissRequest = { showAddDialog = false },
                    title = { Text("Add Course") },
                    text = { Text("Are you sure you want to add '${courseToAdd!!.title}'?") },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.addCourse(courseToAdd!!.id.toString())
                            showAddDialog = false
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Course added successfully")
                            }
                        }) {
                            Text("Add")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAddDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            if (showUpdateDialog && selectedCourse != null) {
                UpdateCourseDialog(
                    course = selectedCourse!!,
                    onDismiss = { showUpdateDialog = false },
                    onSubmit = { updatedRequest ->
                        viewModel.updateCourse(selectedCourse!!.id.toString(), updatedRequest)
                        showUpdateDialog = false
                    }
                )
            }

            if (showDeleteDialog && courseToDelete != null) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Delete Course") },
                    text = { Text("Are you sure you want to delete ${courseToDelete?.title}?") },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.deleteCourse(courseToDelete!!.id.toString())
                            showDeleteDialog = false
                        }) {
                            Text("Delete", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }

}

@Preview
@Composable
fun AddCoursePreview() {
    AddCourse()
}
