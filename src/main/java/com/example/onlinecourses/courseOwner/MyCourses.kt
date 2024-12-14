package com.example.onlinecourses.courseOwner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
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
import kotlinx.coroutines.launch

data class Course(val title: String, val author: String, val description: String, val category: String)

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MyCoursesPreview() {
    MyCourses()
}

@Composable
fun MyCourses() {
    // Данные курсов
    val courseList = listOf(
        Course("Курс 1", "Автор 1", "Описание курса 1", "Программирование"),
        Course("Курс 2", "Автор 2", "Описание курса 2", "Дизайн"),
        Course("Курс 3", "Автор 3", "Описание курса 3", "Маркетинг")
    )

    // Состояния
    var searchQuery by remember { mutableStateOf("") }
    var filteredCourses by remember { mutableStateOf(courseList) }
    val categories = listOf("Программирование", "Дизайн", "Маркетинг")
    val selectedCategories = remember { mutableStateMapOf(*categories.map { it to false }.toTypedArray()) }
    val drawerState = remember { DrawerState(initialValue = DrawerValue.Closed) }
    val scope = rememberCoroutineScope()

    // Проверка, применены ли фильтры
    val areFiltersApplied by derivedStateOf {
        selectedCategories.values.any { it }
    }

    OnlineCursesTheme {
        AppBarCourseOwner(title = "Мои курсы", showTopBar = true, showBottomBar = true) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        Column {
                            Text("Фильтры", style = MaterialTheme.typography.titleMedium)
                            Spacer(
                                modifier = Modifier
                                    .height(8.dp)
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.primary)
                                    .height(1.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Категории", style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                items(categories) { category ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = selectedCategories[category] ?: false,
                                            onCheckedChange = { selectedCategories[category] = it }
                                        )
                                        Text(
                                            text = category,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                            Spacer(
                                modifier = Modifier
                                    .height(8.dp)
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.primary)
                                    .height(1.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Кнопка отмены фильтров
                                Button(
                                    onClick = {
                                        selectedCategories.keys.forEach {
                                            selectedCategories[it] = false
                                        }
                                        filteredCourses = courseList
                                        scope.launch { drawerState.close() }
                                    }
                                ) {
                                    Text("Отменить")
                                }
                                // Кнопка применения фильтров
                                Button(
                                    onClick = {
                                        val activeCategories =
                                            selectedCategories.filterValues { it }.keys
                                        filteredCourses = if (activeCategories.isEmpty()) {
                                            courseList
                                        } else {
                                            courseList.filter { it.category in activeCategories }
                                        }
                                        scope.launch { drawerState.close() }
                                    }
                                ) {
                                    Text("Применить")
                                }
                            }
                        }
                    }
                }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        // Строка поиска
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = {
                                    searchQuery = it
                                    filteredCourses = courseList.filter { course ->
                                        course.title.contains(
                                            searchQuery,
                                            true
                                        ) || course.author.contains(searchQuery, true)
                                    }
                                },
                                label = { Text("Поиск") },
                                modifier = Modifier.weight(1f).padding(end = 8.dp)
                            )
                            Button(
                                onClick = { scope.launch { drawerState.open() } },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (areFiltersApplied) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = "Фильтр"
                                )
                                Text("Фильтр", modifier = Modifier.padding(start = 8.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Список курсов
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(filteredCourses) { course ->
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
                                            text = "Название: ${course.title}",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = "Автор: ${course.author}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            text = "Описание: ${course.description}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Плавающая кнопка
                    FloatingActionButton(
                        onClick = { /* Добавление нового курса */ },
                        containerColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Добавить",
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }
            }
        }
    }
}
