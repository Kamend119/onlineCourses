package com.example.onlinecourses.courseOwner.createCourse

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
fun CreateLecture() {
    var text by remember { mutableStateOf("") }

    OnlineCursesTheme {
        AppBarCourseOwner(title = "Добавить лекционный шаг", showTopBar = true, showBottomBar = true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Введите текст лекции") },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        // Обработчик сохранения
                    },
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text("Сохранить")
                }
            }
        }
    }
}
