package com.example.onlinecourses.administrator.coursesManagement

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.AppBarCourseOwner
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LectureManagement() {
    var text by remember { mutableStateOf("") }

    OnlineCursesTheme {
        AppBarCourseOwner(title = "Проверка шага", showTopBar = true, showBottomBar = true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Лекция") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {

                    },
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text("Удалить")
                }
            }
        }
    }
}
