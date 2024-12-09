package com.example.onlinecourses.courseOwner.checkingProgress

import androidx.compose.foundation.BorderStroke
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
import com.example.onlinecourses.AppBarCourseOwner
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainCheckingPreview() {
    MainChecking()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainChecking() {
    // Список курсов
    val courseList = listOf("Курс 1", "Курс 2", "Курс 3")

    // Шаги для каждого курса
    val stepsMap = mapOf(
        "Курс 1" to listOf("Шаг 1.1", "Шаг 1.2", "Шаг 1.3"),
        "Курс 2" to listOf("Шаг 2.1", "Шаг 2.2"),
        "Курс 3" to listOf("Шаг 3.1", "Шаг 3.2", "Шаг 3.3", "Шаг 3.4")
    )

    var selectedCourse by remember { mutableStateOf<String?>(null) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    OnlineCursesTheme {
        AppBarCourseOwner(title = "Проверка", showTopBar = true, showBottomBar = true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
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
                    val steps = stepsMap[course] ?: emptyList()

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(steps.size) { index ->
                            val step = steps[index]
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        println("Переход к шагу: $step из курса: $course")
                                    },
                                elevation = CardDefaults.cardElevation(2.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "Шаг ${index + 1}: $step",
                                        style = MaterialTheme.typography.bodyLarge,
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
