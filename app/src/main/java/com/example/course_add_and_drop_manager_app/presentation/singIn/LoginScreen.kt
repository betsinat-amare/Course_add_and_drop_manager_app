package com.example.course_add_and_drop_manager_app.presentation.singIn

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.course_add_and_drop_manager_app.Course_add_and_drop_managerAppRoute
import com.example.course_add_and_drop_manager_app.R
import com.example.course_add_and_drop_manager_app.Screen
import com.example.course_add_and_drop_manager_app.data.local.DataStoreManager
import com.example.course_add_and_drop_manager_app.presentation.components.ButtonComponent
import com.example.course_add_and_drop_manager_app.presentation.components.ClickableSignUpTextComponent
import com.example.course_add_and_drop_manager_app.presentation.components.HeadingTextComponent
import com.example.course_add_and_drop_manager_app.presentation.components.NormalTextComponent
import com.example.course_add_and_drop_manager_app.presentation.components.PasswordLoginTextFieldComponent
import com.example.course_add_and_drop_manager_app.presentation.components.TextFieldComponent
import com.example.course_add_and_drop_manager_app.presentation.components.UnderLinedTextComponent
import com.example.course_add_and_drop_manager_app.presentation.components.SystemBackButtonHandler
import com.example.course_add_and_drop_manager_app.ui.theme.colorGrayBackground

//@Composable
//fun LoginScreen(
//    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
//    navigateToDashboard: () -> Unit = {
//        Course_add_and_drop_managerAppRoute.navigateTo(Screen.UserDashboardScreen)
//    }
//) {
//    val username by viewModel.username
//    val password by viewModel.password
//    val loginError by viewModel.loginError
//    val isButtonEnabled by viewModel.isButtonEnabled
//    val context = LocalContext.current
//
//    val onBack: () -> Unit = {
//        Course_add_and_drop_managerAppRoute.navigateTo(Screen.HomeScreen)
//    }
//
//    SystemBackButtonHandler {
//        onBack()
//    }
//
//    Surface(
//        color = colorGrayBackground,
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFE0E7FF))
//            .padding(top = 20.dp),
//    ) {
//        Column {
//            IconButton(onClick = { onBack() }) {
//                Icon(
//                    imageVector = Icons.Default.ArrowBack,
//                    contentDescription = "Back"
//                )
//            }
//
//            Column(modifier = Modifier.fillMaxSize().padding(top = 140.dp)) {
//                HeadingTextComponent(value = stringResource(id = R.string.access))
//                Spacer(modifier = Modifier.height(10.dp))
//                NormalTextComponent(value = stringResource(id = R.string.access_your_course))
//                Spacer(modifier = Modifier.height(25.dp))
//
//                TextFieldComponent(
//                    labelValue = stringResource(id = R.string.placeName),
//                    painterResource = painterResource(id = R.drawable.profile),
//                    contentDescription = stringResource(id = R.string.profileImg),
//                    onValueChange = { viewModel.onUsernameChanged(it) }
//                )
//
//                PasswordLoginTextFieldComponent(
//                    labelValue = stringResource(id = R.string.password),
//                    painterResource = painterResource(id = R.drawable.password),
//                    contentDescription = stringResource(id = R.string.passwordImg),
//                    onValueChange = { viewModel.onPasswordChanged(it) }
//                )
//
//                Spacer(modifier = Modifier.height(15.dp))
//
//                UnderLinedTextComponent(onTextSelected = {
//                    Course_add_and_drop_managerAppRoute.navigateTo(Screen.ForgetPasswordScreen)
//                })
//
//                Spacer(modifier = Modifier.height(40.dp))
//
//                ButtonComponent(
//                    value = stringResource(id = R.string.logIn),
//                    onClick = {
//                        viewModel.login(
//                            onSuccess = {
//                                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
//                                navigateToDashboard()
//                            },
//                            onError = {
//                                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
//                            }
//                        )
//                    },
//                    enabled = isButtonEnabled // Button is enabled or disabled based on input
//                )
//
//                Spacer(modifier = Modifier.height(15.dp))
//
//                NormalTextComponent(value = stringResource(id = R.string.need_to_create))
//                ClickableSignUpTextComponent(onTextSelected = {
//                    Course_add_and_drop_managerAppRoute.navigateTo(Screen.SignUpScreen)
//                })
//
//                if (loginError.isNotBlank()) {
//                    Spacer(modifier = Modifier.height(10.dp))
//                    Text(text = loginError, color = Color.Red, modifier = Modifier.padding(start = 16.dp))
//                }
//            }
//        }
//    }
//}

@Composable
fun LoginScreen(
    navigateToAdminDashboard: () -> Unit = {
        Course_add_and_drop_managerAppRoute.navigateTo(Screen.AdminDashboard)
    },
    navigateToUserDashboard: () -> Unit = {
        Course_add_and_drop_managerAppRoute.navigateTo(Screen.UserDashboardScreen)
    }
) {
    val context = LocalContext.current

    // ✅ Create DataStoreManager and ViewModel manually
    val dataStoreManager = remember { DataStoreManager(context) }
    val viewModel = remember { LoginViewModel(dataStoreManager) }

    val username by viewModel.username
    val password by viewModel.password
    val loginError by viewModel.loginError
    val isButtonEnabled by viewModel.isButtonEnabled



    val onBack: () -> Unit = {
        Course_add_and_drop_managerAppRoute.navigateTo(Screen.HomeScreen)
    }

    SystemBackButtonHandler {
        onBack()
    }

    Surface(
        color = colorGrayBackground,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0E7FF))
            .padding(top = 20.dp),
    ) {
        Column {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 140.dp)
            ) {

                HeadingTextComponent(value = stringResource(id = R.string.access))
                Spacer(modifier = Modifier.height(10.dp))
                NormalTextComponent(value = stringResource(id = R.string.access_your_course))
                Spacer(modifier = Modifier.height(25.dp))

                TextFieldComponent(
                    labelValue = stringResource(id = R.string.placeName),
                    painterResource = painterResource(id = R.drawable.profile),
                    contentDescription = stringResource(id = R.string.profileImg),
                    onValueChange = { viewModel.onUsernameChanged(it) }
                )

                PasswordLoginTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.password),
                    contentDescription = stringResource(id = R.string.passwordImg),
                    onValueChange = { viewModel.onPasswordChanged(it) }
                )

                Spacer(modifier = Modifier.height(15.dp))

                UnderLinedTextComponent(onTextSelected = {
                    Course_add_and_drop_managerAppRoute.navigateTo(Screen.ForgetPasswordScreen)
                })

                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.logIn),
                    onClick = {
                        viewModel.login(
                            onAdminSuccess = {
                                Toast.makeText(context, "Welcome Admin!", Toast.LENGTH_SHORT).show()
                                navigateToAdminDashboard()
//                                Course_add_and_drop_managerAppRoute.navigateTo(Screen.AdminDashboard)
                            },
                            onUserSuccess = {
                                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                                navigateToUserDashboard()
//                                Course_add_and_drop_managerAppRoute.navigateTo(Screen.U)
                            },
                            onError = { error ->
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    enabled = isButtonEnabled
                )

                Spacer(modifier = Modifier.height(15.dp))

                NormalTextComponent(value = stringResource(id = R.string.need_to_create))
                ClickableSignUpTextComponent(onTextSelected = {
                    Course_add_and_drop_managerAppRoute.navigateTo(Screen.SignUpScreen)
                })

                if (loginError.isNotBlank()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = loginError,
                        color = Color.Red,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}