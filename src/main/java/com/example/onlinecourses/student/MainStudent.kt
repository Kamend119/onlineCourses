package com.example.onlinecourses.student

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.AppBarStudent
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainStudent() {
    // Пример данных для средней оценки
    val courses = listOf(
        "Курс 1" to 4.5,
        "Курс 2" to 3.8,
        "Курс 3" to 4.0,
        "Курс 4" to 4.2
    )

    // Пример данных для завершённых заданий по дням
    val dailyStats = remember {
        listOf(
            0, 2, 5, 7, 10, 1, 3,
            4, 6, 8, 9, 11, 2, 1,
            0, 3, 5, 7, 2, 6, 8,
            1, 4, 6, 9, 3, 5, 7
        )
    }

    // Состояние для отображения всплывающего окна
    var showDialog by remember { mutableStateOf(false) }
    var selectedTaskCount by remember { mutableStateOf(0) }

    // Функция для вычисления цвета в зависимости от количества выполненных заданий
    fun getCellColor(taskCount: Int): Color {
        return when (taskCount) {
            in 0..2 -> Color(0xFFE0E0E0) // Светло-серый
            in 3..5 -> Color(0xFFD9A7FF) // Светло-фиолетовый
            in 6..8 -> Color(0xFFBF68FF) // Фиолетовый
            in 9..10 -> Color(0xFFB645FF) // Ярко-фиолетовый
            else -> Color(0xFFD175FF) // Темно-фиолетовый
        }
    }

    OnlineCursesTheme {
        AppBarStudent(title = "Главная", showTopBar = true, showBottomBar = true) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Блок статистики по дням
                Text(
                    text = "Статистика по дням",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                ) {
                    for (i in 0 until 4) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (j in 0 until 7) {
                                val taskCount = dailyStats[i * 7 + j]
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .border(1.dp, Color.Gray)
                                        .background(getCellColor(taskCount))
                                        .clickable {
                                            selectedTaskCount = taskCount
                                            showDialog = true
                                        }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                // Блок среднего балла
                Text(
                    text = "Средний балл",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(courses) { (courseName, averageScore) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = courseName, style = MaterialTheme.typography.bodyLarge)
                            Text(text = "${String.format("%.1f", averageScore)}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }

            // Всплывающее окно
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Ок")
                        }
                    },
                    title = { Text("Количество заданий") },
                    text = { Text("В этот день выполнено $selectedTaskCount заданий.") }
                )
            }
        }
    }
}
