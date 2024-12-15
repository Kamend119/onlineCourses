package com.example.onlinecourses.student.searchCourses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.AppBarStudent
import com.example.onlinecourses.courseOwner.editCourse.CourseStep
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CoursesPreview() {
    var coursesName by remember { mutableStateOf("") }
    var coursesDescription by remember { mutableStateOf("") }

    // Пример шагов
    val stepsList = remember {
        mutableStateListOf(
            CourseStep(stepType = "Тест", stepNumber = 1),
            CourseStep(stepType = "Видео", stepNumber = 2),
            CourseStep(stepType = "Практика", stepNumber = 3)
        )
    }

    OnlineCursesTheme {
        AppBarStudent("Просмотр курса", showTopBar = true, showBottomBar = true) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    OutlinedTextField(
                        value = coursesName,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Наименование") },
                        modifier = Modifier.padding(bottom = 16.dp),
                        textStyle = MaterialTheme.typography.bodyMedium
                    )

                    OutlinedTextField(
                        value = coursesDescription,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Описание") },
                        modifier = Modifier.padding(bottom = 16.dp),
                        textStyle = MaterialTheme.typography.bodyMedium
                    )

                    Row(
                        Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.End
                    ){
                        Button(
                            onClick = {

                            }, Modifier.padding(4.dp)
                        ) {
                            Text("Поступить")
                        }

                        Button(
                            onClick = {

                            }, Modifier.padding(4.dp)
                        ) {
                            Text("Отложить")
                        }
                    }
                }
            }
        }
    }
}