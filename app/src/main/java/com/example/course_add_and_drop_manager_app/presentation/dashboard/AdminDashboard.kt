import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.course_add_and_drop_manager_app.Course_add_and_drop_managerAppRoute
import com.example.course_add_and_drop_manager_app.R
import com.example.course_add_and_drop_manager_app.Screen
import com.example.course_add_and_drop_manager_app.data.local.DataStoreManager
import com.example.course_add_and_drop_manager_app.presentation.components.*
import com.example.course_add_and_drop_manager_app.presentation.dashboard.CourseViewModel
import com.example.course_add_and_drop_manager_app.ui.theme.colorAccent
import com.example.course_add_and_drop_manager_app.ui.theme.colorGrayBackground

@Composable
fun AdminDashboard(viewModel: CourseViewModel = viewModel()) {

    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }

    val tokenState = produceState<String?>(initialValue = null) {
        dataStoreManager.token.collect {
            value = it
        }
    }

    val token = tokenState.value

    if (token == null) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        return
    }

    SystemBackButtonHandler {
        Course_add_and_drop_managerAppRoute.navigateTo(Screen.LoginScreen)
    }

    Surface(color = colorGrayBackground, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(modifier = Modifier.weight(1f)) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(colorAccent)
                ) {
                    IconButton(
                        onClick = {
                            Course_add_and_drop_managerAppRoute.navigateTo(Screen.LoginScreen)
                        },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 15.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }

                    IconButton(
                        onClick = {
                            Course_add_and_drop_managerAppRoute.navigateTo(Screen.AllAddsScreen)
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 15.dp, end = 10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }

                    ProfileImagePlaceholder(modifier = Modifier.align(Alignment.Center))
                }

                // Input Fields
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)) {

                    TextFieldAdminComponent(
                        labelValue = stringResource(id = R.string.title),
                        painterResource = painterResource(id = R.drawable.profile),
                        onValueChange = { viewModel.title.value = it }
                    )
                    TextFieldAdminComponent(
                        labelValue = stringResource(id = R.string.code),
                        painterResource = painterResource(id = R.drawable.profile),
                        onValueChange = { viewModel.code.value = it }
                    )
                    TextFieldAdminComponent(
                        labelValue = stringResource(id = R.string.description),
                        painterResource = painterResource(id = R.drawable.profile),
                        onValueChange = { viewModel.description.value = it }
                    )
                    TextFieldAdminFinalComponent(
                        labelValue = stringResource(id = R.string.credit_hours),
                        painterResource = painterResource(id = R.drawable.profile),
                        onValueChange = { viewModel.creditHours.value = it }
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(value = stringResource(id = R.string.save), onClick = {
                        viewModel.createCourse(token)
                    })

                    if (viewModel.successMessage.value.isNotEmpty()) {
                        Text(viewModel.successMessage.value, color = Color.Green)
                    }
                    if (viewModel.errorMessage.value.isNotEmpty()) {
                        Text(viewModel.errorMessage.value, color = Color.Red)
                    }
                }
            }

            // Footer at bottom
            FooterAdmin(
                currentScreen = Course_add_and_drop_managerAppRoute.currentScreen.value,
                onItemSelected = { selectedScreen ->
                    Course_add_and_drop_managerAppRoute.navigateTo(selectedScreen)
                }
            )
        }
    }
} 