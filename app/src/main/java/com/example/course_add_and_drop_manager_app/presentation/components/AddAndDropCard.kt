package com.example.course_add_and_drop_manager_app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.course_add_and_drop_manager_app.data.local.DataStoreManager
import com.example.course_add_and_drop_manager_app.data.model.Course
import com.example.course_add_and_drop_manager_app.data.repository.CourseRepository
import com.example.course_add_and_drop_manager_app.presentation.dashboard.CourseViewModel
import com.example.course_add_and_drop_manager_app.ui.theme.colorPrimary
import com.example.course_add_and_drop_manager_app.ui.theme.white
@Composable
fun AddCard(
    onAddClick: () -> Unit,
    header: String,
    text1: String,
    text2: String,
    actions: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .width(320.dp)
                .background(color = white, shape = RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFFFFFFFF), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = header, fontSize = 18.sp, color = Color.Black)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = text1, fontSize = 14.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = text2, fontSize = 14.sp, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Call the parent-defined handler
                    ButtonAddAndDrop(value = "Add now", onClick = onAddClick)
                }

                Spacer(modifier = Modifier.height(8.dp))
                actions()
            }
        }
    }
}



@Composable
fun DropCard(
    onClick: @Composable () -> Unit,
    header: String,
    text1: String,
    text2: String,
    actions: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .width(320.dp)
                .background(color = white, shape = RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFFFFFFFF), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = header,
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color.Black,
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = text1,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = text2,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    ButtonAddAndDrop(
                        value = "Drop now",
                        onClick = {  }
                    )
                }

                // 🔥 Insert the actions (Update & Delete buttons) here
                Spacer(modifier = Modifier.height(8.dp))
                actions()
            }
        }
    }
}



@Composable
fun ButtonAddAndDrop(
    value: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(90.dp)
            .height(36.dp)
            .border(
                width = 1.dp,
                color = colorPrimary,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = colorPrimary
        ),
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
    ) {
        Text(
            text = value,
            style = TextStyle(
                fontSize = 12.sp,
                color = colorPrimary
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddAndDropCardPreview() {
//    AddCard(
//        onClick = {},
//        header = "Python Starter",
//        text1 = "Learn Python basics",
//        text2 = "Duration: 6 weeks"
//    )
}
