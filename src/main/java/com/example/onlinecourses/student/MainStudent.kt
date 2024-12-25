package com.example.onlinecourses.student

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlinecourses.AppBarStudent
import com.example.onlinecourses.network.MainStudentViewModel
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
fun MainStudent(navController: NavHostController, userId: String, role: String) {
    val studentViewModel: MainStudentViewModel = viewModel()
    val state by studentViewModel.dailyStatsState.collectAsState()

    LaunchedEffect(userId) {
        studentViewModel.fetchDailyStats(userId.toInt()) // Загружаем данные по статистике
    }

    var showDialog by remember { mutableStateOf(false) }
    var selectedTaskCount by remember { mutableStateOf(0) }
    var selectedDate by remember { mutableStateOf("") }

    fun getCellColor(taskCount: Int): Color {
        return when (taskCount) {
            in 0..2 -> Color(0xFFE0E0E0)
            in 3..5 -> Color(0xFFD9A7FF)
            in 6..8 -> Color(0xFFBF68FF)
            in 9..10 -> Color(0xFFB645FF)
            else -> Color(0xFFD175FF)
        }
    }

    OnlineCursesTheme {
        AppBarStudent(title = "Главная", showTopBar = true, showBottomBar = true, navController, userId) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Статистика по дням",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )

                if (state.isLoading) {
                    Text("Загрузка данных...", color = MaterialTheme.colorScheme.onBackground)
                } else if (state.error != null) {
                    Text("Ошибка: ${state.error}", color = MaterialTheme.colorScheme.error)
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp)
                    ) {
                        state.data.chunked(7).forEachIndexed { index, weekStats ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                weekStats.forEachIndexed { dayIndex, dateStep ->
                                    val taskCount = dateStep.stepsCompleted
                                    val date = dateStep.dateCompleted

                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .border(1.dp, Color.Gray)
                                            .background(getCellColor(taskCount))
                                            .clickable {
                                                selectedTaskCount = taskCount
                                                selectedDate = date
                                                showDialog = true
                                            },
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(
                                            text = taskCount.toString(),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Ок", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    },
                    title = { Text("Количество заданий", color = MaterialTheme.colorScheme.onSurface) },
                    text = {
                        Text("В день $selectedDate выполнено $selectedTaskCount заданий.", color = MaterialTheme.colorScheme.onSurface)
                    }
                )
            }
        }
    }
}
