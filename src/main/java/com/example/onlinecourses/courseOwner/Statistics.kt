package com.example.onlinecourses.courseOwner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.AppBarCourseOwner
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

// Данные для отображения статистики
data class UserStatistics(
    val name: String,
    val averageScore: Double,
    val tasksCompletedPercentage: Int
)

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun StatisticsPreview() {
    Statistics()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Statistics() {
    // Список курсов
    val courseList = listOf("Курс 1", "Курс 2", "Курс 3")

    // Статистика пользователей
    val statisticsMap = mapOf(
        "Курс 1" to listOf(
            UserStatistics("Иван Иванов", 4.5, 80),
            UserStatistics("Мария Петрова", 4.8, 90),
            UserStatistics("Дмитрий Смирнов", 4.0, 70)
        ),
        "Курс 2" to listOf(
            UserStatistics("Анна Сидорова", 4.2, 85),
            UserStatistics("Алексей Кузнецов", 4.9, 95)
        ),
        "Курс 3" to listOf(
            UserStatistics("Ольга Попова", 4.7, 88),
            UserStatistics("Сергей Васильев", 4.3, 75),
            UserStatistics("Екатерина Михайлова", 4.6, 92)
        )
    )

    var selectedCourse by remember { mutableStateOf<String?>(null) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    OnlineCursesTheme {
        AppBarCourseOwner(title = "Статистика", showTopBar = true, showBottomBar = true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // Выпадающий список для выбора курса
                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCourse ?: "Выберите курс",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Курс") },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false }
                    ) {
                        courseList.forEach { course ->
                            DropdownMenuItem(
                                text = { Text(course) },
                                onClick = {
                                    selectedCourse = course
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                selectedCourse?.let { course ->
                    // Отображение статистики пользователей
                    val usersStatistics = statisticsMap[course] ?: emptyList()

                    Text(
                        text = "Пользователи: ${usersStatistics.size}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Активные пользователи: ${usersStatistics.count { it.tasksCompletedPercentage > 50 }}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(usersStatistics.size) { index ->
                            val userStats = usersStatistics[index]

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(2.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "Имя: ${userStats.name}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Средний балл: ${userStats.averageScore}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Процент выполненных заданий: ${userStats.tasksCompletedPercentage}%",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
