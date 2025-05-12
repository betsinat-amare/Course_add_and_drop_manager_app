package com.example.course_add_and_drop_manager_app.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.course_add_and_drop_manager_app.Course_add_and_drop_managerAppRoute
import com.example.course_add_and_drop_manager_app.R
import com.example.course_add_and_drop_manager_app.Screen
import com.example.course_add_and_drop_manager_app.presentation.components.Button
import com.example.course_add_and_drop_manager_app.presentation.components.ButtonComponent
import com.example.course_add_and_drop_manager_app.presentation.components.HeadingHomeTextComponent
import com.example.course_add_and_drop_manager_app.presentation.components.HeadingTextComponent
import com.example.course_add_and_drop_manager_app.presentation.components.NormalTextComponent
import com.example.course_add_and_drop_manager_app.ui.theme.colorGrayBackground

@Composable
fun HomeScreen(){
    Surface(
        color = colorGrayBackground,
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFE0E7FF))
//            .padding(top = 30.dp),
    ){

        Column(modifier = Modifier.fillMaxSize().padding(top = 160.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp), // Optional padding
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo), // Use your actual image name
                    contentDescription = "App Logo",
                    modifier = Modifier.size(120.dp), // Adjust size as needed
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            HeadingHomeTextComponent(value = stringResource(id= R.string.welcome))
            NormalTextComponent(value= stringResource(id=R.string.effort))
            Spacer(modifier=Modifier.height(180.dp))
            Button(value = stringResource(id=R.string.getStarted), onClick = {
                Course_add_and_drop_managerAppRoute.navigateTo(Screen.LoginScreen)
            })
        }

    }
}
@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}