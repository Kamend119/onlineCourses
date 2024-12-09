package com.example.onlinecourses.courseOwner.createCourse

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.AppBarCourseOwner
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CreateStep() {
    OnlineCursesTheme {
        AppBarCourseOwner(title = "Создание шага", showTopBar = true, showBottomBar = true) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Button(
                    onClick = {
                        // Обработка сохранения
                    }
                ) {
                    Text("Лекция")
                }
                Button(
                    onClick = {
                        // Обработка сохранения
                    }
                ) {
                    Text("Вопрос")
                }
            }
        }
    }
}
