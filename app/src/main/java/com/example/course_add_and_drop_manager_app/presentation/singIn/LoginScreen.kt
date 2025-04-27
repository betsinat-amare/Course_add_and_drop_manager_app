package com.example.course_add_and_drop_manager_app.presentation.singIn

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.course_add_and_drop_manager_app.Course_add_and_drop_managerAppRoute
import com.example.course_add_and_drop_manager_app.R
import com.example.course_add_and_drop_manager_app.Screen
import com.example.course_add_and_drop_manager_app.presentation.components.ButtonComponent
import com.example.course_add_and_drop_manager_app.presentation.components.ClickableSignUpTextComponent
import com.example.course_add_and_drop_manager_app.presentation.components.HeadingTextComponent
import com.example.course_add_and_drop_manager_app.presentation.components.NormalTextComponent
import com.example.course_add_and_drop_manager_app.presentation.components.PasswordLoginTextFieldComponent
import com.example.course_add_and_drop_manager_app.presentation.components.PasswordTextFieldComponent
import com.example.course_add_and_drop_manager_app.presentation.components.SystemBackButtonHandler
import com.example.course_add_and_drop_manager_app.presentation.components.TextFieldComponent
import com.example.course_add_and_drop_manager_app.presentation.components.UnderLinedTextComponent
import com.example.course_add_and_drop_manager_app.ui.theme.colorGrayBackground

@Composable
fun LoginScreen() {
    val onBack: () -> Unit = {
        Course_add_and_drop_managerAppRoute.navigateTo(Screen.HomeScreen)
    }

    SystemBackButtonHandler {
        onBack()
    }
    Surface(
        color = colorGrayBackground,
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFE0E7FF))
            .padding(top = 20.dp),
    ) {
        Column {
            // Back Icon
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }


            Column(modifier = Modifier.fillMaxSize().padding(top = 140.dp)) {
                HeadingTextComponent(value = stringResource(id = R.string.access))
                Spacer(modifier = Modifier.height(10.dp))
                NormalTextComponent(value = stringResource(id = R.string.access_your_course))
                Spacer(modifier = Modifier.height(25.dp))
                TextFieldComponent(
                    labelValue = stringResource(id = R.string.Id),
                    painterResource = (painterResource(id = R.drawable.id_image)),
                    contentDescription = stringResource(id = R.string.IdImg)

                )
                PasswordLoginTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = (painterResource(id = R.drawable.password)),
                    contentDescription = stringResource(id = R.string.passwordImg)

                )
                Spacer(modifier = Modifier.height(15.dp))
                UnderLinedTextComponent( onTextSelected = {
                    Course_add_and_drop_managerAppRoute.navigateTo(Screen.ForgetPasswordScreen)

                })
                Spacer(modifier = Modifier.height(40.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.logIn),
                    onClick = {
                        Course_add_and_drop_managerAppRoute.navigateTo(Screen.UserDashboardScreen)
                    })
                Spacer(modifier = Modifier.height(15.dp))
                NormalTextComponent(value = stringResource(id = R.string.need_to_create))
                ClickableSignUpTextComponent(onTextSelected = {
                    Course_add_and_drop_managerAppRoute.navigateTo(Screen.SignUpScreen)
                })

            }


        }
    }

}
@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}