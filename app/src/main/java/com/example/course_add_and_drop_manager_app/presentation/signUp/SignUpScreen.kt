package com.example.course_add_and_drop_manager_app.presentation.signUp

import SignupViewModel
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.course_add_and_drop_manager_app.Course_add_and_drop_managerAppRoute
import com.example.course_add_and_drop_manager_app.R
import com.example.course_add_and_drop_manager_app.Screen
import com.example.course_add_and_drop_manager_app.presentation.components.*
import com.example.course_add_and_drop_manager_app.ui.theme.colorGrayBackground
import com.example.course_add_and_drop_manager_app.ui.theme.colorPrimary

@Composable
fun SignUpScreen(viewModel: SignupViewModel = viewModel()) {
    val fullName by viewModel.full_name
    val email by viewModel.email
    val password by viewModel.password
    val profilePhoto by viewModel.profile_photo
    val errorMessage by viewModel.errorMessage
    val isSignupSuccessful by viewModel.isSignupSuccessful


    val context = LocalContext.current

    // Launcher for image picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.profile_photo.value = it.toString() // Store Uri as string
        }
    }

    // State for checkbox
    var isChecked by remember { mutableStateOf(false) }

    val isButtonEnabled by remember {
        derivedStateOf {
            // Button is enabled only if essential fields are filled, valid, and checkbox is checked
            fullName.isNotBlank() && email.isNotBlank() && password.length >= 6 && email.contains("@") && isChecked
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorGrayBackground)
            .padding(top = 140.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 30.dp)
        ) {
            HeadingTextComponent(value = stringResource(id = R.string.Start))
            Spacer(modifier = Modifier.height(10.dp))
            NormalTextComponent(value = stringResource(id = R.string.Hello))
            Spacer(modifier = Modifier.height(35.dp))

            TextFieldComponent(
                labelValue = stringResource(id = R.string.placeName),
                painterResource = painterResource(id = R.drawable.profile),
                contentDescription = stringResource(id = R.string.profileImg),
                onValueChange = { viewModel.full_name.value = it }
            )
            TextFieldComponent(
                labelValue = stringResource(id = R.string.email),
                painterResource = painterResource(id = R.drawable.email),
                contentDescription = stringResource(id = R.string.email),
                onValueChange = { viewModel.email.value = it }
            )
            TextFieldComponent(
                labelValue = stringResource(id = R.string.username),
                painterResource = painterResource(id = R.drawable.id_image),
                contentDescription = stringResource(id = R.string.username),
                onValueChange = { viewModel.username.value = it }
            )
            TextFieldComponent(
                labelValue = stringResource(id = R.string.Id),
                painterResource = painterResource(id = R.drawable.id_image),
                contentDescription = stringResource(id = R.string.IdImg),
                onValueChange = { viewModel.id.value = it }
            )
            PasswordTextFieldComponent(
                labelValue = stringResource(id = R.string.password),
                painterResource = painterResource(id = R.drawable.password),
                contentDescription = stringResource(id = R.string.passwordImg),
                onValueChange = { viewModel.password.value = it }
            )
            TextFieldPhotoComponent(
                labelValue = stringResource(id = R.string.upload),
                painterResource = painterResource(id = R.drawable.upload),
                contentDescription = stringResource(id = R.string.uploadImg),
                onValueChange = {
                    // Instead of direct text input, launch image picker
                    imagePickerLauncher.launch("image/*")
                }
            )
            // Checkbox component to agree to terms and conditions
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Text(
                text = buildAnnotatedString {
                    append("Agree with ")
                    withStyle(style = SpanStyle(color = colorPrimary)) {
                        append("terms ")
                    }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append("and ")
                    }
                    withStyle(style = SpanStyle(color = colorPrimary)) {
                        append("conditions")
                    }
                },
                modifier = Modifier.clickable {
                    Course_add_and_drop_managerAppRoute.navigateTo(Screen.TermsAndConditionsScreen)// Navigate to the Terms and Conditions screen
                }
            )


            Spacer(modifier = Modifier.height(20.dp))

            // Button is disabled if essential fields are not filled in or checkbox is unchecked
            ButtonComponent(
                value = stringResource(id = R.string.signUp),
                onClick = {
                    viewModel.signup(
                        onSuccess = {
                            Toast.makeText(context, "Signup successful!", Toast.LENGTH_SHORT).show()
                            viewModel.resetForm()
                        },
                        onError = { error ->
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                        }
                    )
                },
                enabled = isButtonEnabled // Disable button if validation fails or checkbox is not checked
            )

            if (errorMessage.isNotBlank()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            if (isSignupSuccessful) {
                Text(
                    text = "Signup successful!",
                    color = Color.Green,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            NormalTextComponent(value = stringResource(id = R.string.Already_have_accountL))
            ClickableLoginTextComponent(onTextSelected = {
                Course_add_and_drop_managerAppRoute.navigateTo(Screen.LoginScreen)
            })

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen()
}

//@Composable
//fun SignUpScreen(viewModel: SignupViewModel = viewModel()) {
//    val fullName by viewModel.full_name
//    val email by viewModel.email
//    val password by viewModel.password
//    val profilePhoto by viewModel.profile_photo
//    val errorMessage by viewModel.errorMessage
//    val isSignupSuccessful by viewModel.isSignupSuccessful
//    val context = LocalContext.current
//
//    val isButtonEnabled by remember {
//        derivedStateOf {
//            // Button is enabled only if essential fields are filled and valid (except profile photo)
//            fullName.isNotBlank() && email.isNotBlank() && password.length >= 6 && email.contains("@")
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(colorGrayBackground)
//            .padding(top = 140.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//                .padding(horizontal = 16.dp)
//                .padding(bottom = 30.dp)
//        ) {
//            HeadingTextComponent(value = stringResource(id = R.string.Start))
//            Spacer(modifier = Modifier.height(10.dp))
//            NormalTextComponent(value = stringResource(id = R.string.Hello))
//            Spacer(modifier = Modifier.height(35.dp))
//
//            TextFieldComponent(
//                labelValue = stringResource(id = R.string.placeName),
//                painterResource = painterResource(id = R.drawable.profile),
//                contentDescription = stringResource(id = R.string.profileImg),
//                onValueChange = { viewModel.full_name.value = it }
//            )
//            TextFieldComponent(
//                labelValue = stringResource(id = R.string.email),
//                painterResource = painterResource(id = R.drawable.email),
//                contentDescription = stringResource(id = R.string.email),
//                onValueChange = { viewModel.email.value = it }
//            )
//            TextFieldComponent(
//                labelValue = stringResource(id = R.string.username),
//                painterResource = painterResource(id = R.drawable.id_image),
//                contentDescription = stringResource(id = R.string.username),
//                onValueChange = { viewModel.username.value = it }
//            )
//            TextFieldComponent(
//                labelValue = stringResource(id = R.string.Id),
//                painterResource = painterResource(id = R.drawable.id_image),
//                contentDescription = stringResource(id = R.string.IdImg),
//                onValueChange = { viewModel.id.value = it }
//            )
//            PasswordTextFieldComponent(
//                labelValue = stringResource(id = R.string.password),
//                painterResource = painterResource(id = R.drawable.password),
//                contentDescription = stringResource(id = R.string.passwordImg),
//                onValueChange = { viewModel.password.value = it }
//            )
//            TextFieldPhotoComponent(
//                labelValue = stringResource(id = R.string.upload),
//                painterResource = painterResource(id = R.drawable.upload),
//                contentDescription = stringResource(id = R.string.uploadImg),
//                onValueChange = { viewModel.profile_photo.value = it }
//            )
//
//            CheckboxComponent(
//                value = stringResource(id = R.string.terms_and_condition),
//                onTextSelected = {
//                    // Navigate to the terms and conditions screen
//                }
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            // Button is disabled if essential fields are not filled in
//            ButtonComponent(
//                value = stringResource(id = R.string.signUp),
//                onClick = {
//                    viewModel.signup(
//                        onSuccess = {
//                            Toast.makeText(context, "Signup successful!", Toast.LENGTH_SHORT).show()
//                            viewModel.resetForm()
//                        },
//                        onError = { error ->
//                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
//                        }
//                    )
//                },
//                enabled = isButtonEnabled // Disable button if validation fails
//            )
//
//            if (errorMessage.isNotBlank()) {
//                Text(
//                    text = errorMessage,
//                    color = Color.Red,
//                    modifier = Modifier.padding(top = 10.dp)
//                )
//            }
//
//            if (isSignupSuccessful) {
//                Text(
//                    text = "Signup successful!",
//                    color = Color.Green,
//                    modifier = Modifier.padding(top = 10.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            NormalTextComponent(value = stringResource(id = R.string.Already_have_accountL))
//            ClickableLoginTextComponent(onTextSelected = {
//                Course_add_and_drop_managerAppRoute.navigateTo(Screen.LoginScreen)
//            })
//
//            Spacer(modifier = Modifier.height(30.dp))
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewSignUpScreen() {
//    SignUpScreen()
//}
