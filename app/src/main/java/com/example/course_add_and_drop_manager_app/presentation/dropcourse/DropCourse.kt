package com.example.course_add_and_drop_manager_app.presentation.dropcourse

import ProfileImgPlaceholder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

import com.example.course_add_and_drop_manager_app.presentation.components.DropCard

import com.example.course_add_and_drop_manager_app.presentation.components.Footer
import com.example.course_add_and_drop_manager_app.presentation.components.SearchBar
import com.example.course_add_and_drop_manager_app.presentation.components.SystemBackButtonHandler
import com.example.course_add_and_drop_manager_app.ui.theme.colorGrayBackground
import com.example.course_add_and_drop_manager_app.data.repository.CourseRepository
import com.example.course_add_and_drop_manager_app.presentation.components.UpdateCourseDialog

//@Composable
//fun DropCourse(){
//    val onBack: () -> Unit = {
//        Course_add_and_drop_managerAppRoute.navigateTo(Screen.SelectAcademicYear)
//    }
//
//    SystemBackButtonHandler {
//        onBack()
//    }
//
//    Surface(
//        color = colorGrayBackground,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState()) // <--- Make it scrollable
//                .padding(horizontal = 16.dp) // optional: horizontal padding
//        ) {
//            Spacer(modifier = Modifier.height(15.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                IconButton(onClick = { onBack() }) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = "Back"
//                    )
//                }
//                Text(
//                    text = "Drop Course",
//                    modifier = Modifier.padding(top = 17.dp),
//                    style = TextStyle(fontSize = 18.sp)
//                )
//                ProfileImgPlaceholder()
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            var query by remember { mutableStateOf("") }
//            SearchBar(
//                query = query,
//                onQueryChange = { query = it }
//            )
//
//            Spacer(modifier = Modifier.height(40.dp))
//
//
//                DropCard(
//                    onClick = {},
//                    header = "Python Starter",
//                    text1 = "Learn Python basics",
//                    text2 = "Duration: 6 weeks"
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//
//            Column(modifier=Modifier.padding(bottom = 10.dp),
//                verticalArrangement = Arrangement.Bottom) {
//                val currentScreen = Course_add_and_drop_managerAppRoute.currentScreen.value
//                Footer(
//                    currentScreen = currentScreen,
//                    onItemSelected = { selectedScreen ->
//                        Course_add_and_drop_managerAppRoute.navigateTo(selectedScreen)
//                    }
//                )
//
//
//
//            }
//        }
//    }
//}

@Composable
fun DropCourse() {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }

    // You must pass repository here too (you can create it from RetrofitInstance.api)
    val viewModel = remember {
        CourseViewModel(
            dataStoreManager = dataStoreManager,
            repository = CourseRepository
        )
    }

    var query by remember { mutableStateOf("") }
    var showUpdateDialog by remember { mutableStateOf(false) }
    var selectedCourse by remember { mutableStateOf<Course?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var courseToDelete by remember { mutableStateOf<Course?>(null) }



    val onBack: () -> Unit = {
        Course_add_and_drop_managerAppRoute.navigateTo(Screen.UserDashboardScreen)
    }

    SystemBackButtonHandler {
        onBack()
    }

    Surface(
        color = colorGrayBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onBack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Drop Course",
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
                Text(text = it, color = androidx.compose.ui.graphics.Color.Red)
            }

            viewModel.courses
                .filter { it.title.contains(query, ignoreCase = true) }
                .forEach { course ->
                    DropCard(
                        onClick = {},
                        header = course.title,
                        text1 = course.description,
                        text2 = "Credit Hours: ${course.credit_hours}",
                        actions = {
                            Row {
                                IconButton(onClick = {
                                    courseToDelete = course
                                    showDeleteDialog = true
                                }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                }
                                IconButton(onClick = {
                                    selectedCourse = course
                                    showUpdateDialog = true
                                }) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Update", tint = Color(0xFF3F51B5))
                                }
                            }
                        }

                    )
                    Spacer(modifier = Modifier.height(10.dp))
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
                    title = {
                        Text("Delete Course")
                    },
                    text = {
                        Text("Are you sure you want to delete ${courseToDelete?.title}?")
                    },
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


            Spacer(modifier = Modifier.height(20.dp))



        }
        Column(modifier=Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom) {
            Footer(
                currentScreen = Course_add_and_drop_managerAppRoute.currentScreen.value,
                onItemSelected = { selectedScreen ->
                    Course_add_and_drop_managerAppRoute.navigateTo(selectedScreen)
                }
            )
        }
    }
}



@Preview
@Composable
fun DropCoursePreview(){
    DropCourse()
}