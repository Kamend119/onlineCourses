package com.example.onlinecourses.courseOwner.editCourse

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.AppBarCourseOwner
import com.example.onlinecourses.R
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

// Модель для шага курса
data class CourseStep(val stepType: String, val stepNumber: Int)

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainEdit() {
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
        AppBarCourseOwner(title = "Редактирование курса", showTopBar = true, showBottomBar = true) {
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
                        onValueChange = { coursesName = it },
                        label = { Text("Наименование") },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = coursesDescription,
                        onValueChange = { coursesDescription = it },
                        label = { Text("Описание") },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(stepsList) { step ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(2.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "Тип шага: ${step.stepType}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Номер шага: ${step.stepNumber}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // Обработка сохранения
                        }
                    ) {
                        Text("Сохранить")
                    }
                }

                FloatingActionButton(
                    onClick = {
                        stepsList.add(CourseStep(stepType = "Новый шаг", stepNumber = stepsList.size + 1))
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Добавить шаг",
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }
    }
}
