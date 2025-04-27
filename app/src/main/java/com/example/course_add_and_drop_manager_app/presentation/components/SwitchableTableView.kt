package com.example.course_add_and_drop_manager_app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.course_add_and_drop_manager_app.Course_add_and_drop_managerAppRoute
import com.example.course_add_and_drop_manager_app.R
import com.example.course_add_and_drop_manager_app.Screen
import com.example.course_add_and_drop_manager_app.ui.theme.colorGrayBackground

@Composable
fun SwitchableTableView() {
    var currentHeaderSet by remember{mutableStateOf(0)} // 0: first set, 1: second set

    val tableData = listOf(
        Triple("Mobile Dev", "2024", "2nd"),
        Triple("AI", "2023", "1st"),
        Triple("Web Dev", "2022", "1st"),
    )

    val statusData = listOf(
        Triple("Mobile Dev", "Approved", "Dr. Fikru"),
        Triple("AI", "Pending", "Dr. Eleni"),
        Triple("Web Dev", "Rejected", "Dr. Abdi"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6ECFF)) // soft blue
            .padding(16.dp)
    ) {

        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorGrayBackground)
                .border(width=2.dp,color=Color.Gray)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Course Name", modifier = Modifier.weight(1f), color = Color.Blue)

            if (currentHeaderSet == 0) {
                Text("Academic Year", modifier = Modifier.weight(1f), color = Color.Blue)
                Text("Semester", modifier = Modifier.weight(1f), color = Color.Blue)
            } else {
                Text("Status", modifier = Modifier.weight(1f), color = Color.Blue)
                Text("Advisor", modifier = Modifier.weight(1f), color = Color.Blue)
            }
        }

        // Data Rows
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(width=2.dp,color=Color.Gray)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .padding(vertical = 8.dp)
        ) {
            val displayData = if (currentHeaderSet == 0) tableData else statusData

            displayData.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(item.first, modifier = Modifier.weight(1f))
                    Text(item.second, modifier = Modifier.weight(1f))
                    Text(item.third, modifier = Modifier.weight(1f))
                }
            }
        }

        // Switch View Button
        Row(
            modifier = Modifier.fillMaxWidth(),

        ) {
            IconButton(onClick = {
                currentHeaderSet = (currentHeaderSet + 1) % 2
            }) {
                Icon(
                    imageVector = if (currentHeaderSet == 0) Icons.Default.ArrowForward else Icons.Default.ArrowBack,
                    contentDescription = "Switch Header View"
                )
            }
        }
        ButtonComponent(value= stringResource(id= R.string.dashboard_button), onClick = {
            Course_add_and_drop_managerAppRoute.navigateTo(Screen.SelectAcademicYear)

        })
    }
}
