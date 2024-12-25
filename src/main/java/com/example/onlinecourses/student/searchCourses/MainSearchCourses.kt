package com.example.onlinecourses.student.searchCourses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlinecourses.AppBarStudent
import com.example.onlinecourses.network.CourseViewModel
import com.example.onlinecourses.ui.theme.OnlineCursesTheme
import kotlinx.coroutines.launch

@Composable
fun MainSearchCourses(navController: NavHostController, userId: String) {
    val focusManager = LocalFocusManager.current
    val courseViewModel: CourseViewModel = viewModel()
    var searchQuery by remember { mutableStateOf("") }
    var filteredCourses by remember { mutableStateOf(courseViewModel.courses.value) }
    val selectedCategories = remember { mutableStateMapOf<String, Boolean>() }
    val drawerState = remember { DrawerState(initialValue = DrawerValue.Closed) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        courseViewModel.fetchCategories()
        courseViewModel.fetchCourses()
    }

    val categories = courseViewModel.categories.value
    val courses = courseViewModel.courses.value

    // Фильтрация курсов на основе поискового запроса и выбранных категорий
    LaunchedEffect(searchQuery, selectedCategories, courses) {
        filteredCourses = if (courses.isEmpty()) {
            emptyList() // Если курсы не загружены, показываем пустой список
        } else if (searchQuery.isBlank() && selectedCategories.isEmpty()) {
            courses // Показываем все курсы, если поисковая строка пуста и категории не выбраны
        } else {
            courses.filter { course ->
                val matchesSearchQuery = course.nameCourse.contains(searchQuery, ignoreCase = true) ||
                        course.fioOwner.contains(searchQuery, ignoreCase = true)
                val matchesCategoryFilter = selectedCategories.keys.isEmpty() || selectedCategories.entries.any { it.value && it.key == course.nameCategory }
                matchesSearchQuery && matchesCategoryFilter
            }
        }
    }

    OnlineCursesTheme {
        AppBarStudent(title = "Поиск курсов", showTopBar = true, showBottomBar = true, navController, userId) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.surface)
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = {
                                    focusManager.clearFocus()
                                })
                            }
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
                                            checked = selectedCategories[category.name] ?: false,
                                            onCheckedChange = { isChecked ->
                                                selectedCategories[category.name] = isChecked
                                            }
                                        )
                                        Text(text = category.name, style = MaterialTheme.typography.bodyMedium)
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
                                Button(
                                    onClick = {
                                        selectedCategories.clear()
                                        filteredCourses = courses
                                        scope.launch { drawerState.close() }
                                    }
                                ) {
                                    Text("Отменить")
                                }
                                Button(
                                    onClick = {
                                        if (selectedCategories.values.none { it }) {
                                            selectedCategories.clear()
                                            filteredCourses = courses
                                        } else {
                                            filteredCourses = courses.filter { course ->
                                                selectedCategories.entries.any { it.value && it.key == course.nameCategory }
                                            }
                                        }
                                        filteredCourses = filteredCourses.filter { course ->
                                            val matchesSearchQuery = course.nameCourse.contains(searchQuery, ignoreCase = true) ||
                                                    course.fioOwner.contains(searchQuery, ignoreCase = true)
                                            matchesSearchQuery
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = {
                                    searchQuery = it
                                },
                                label = { Text("Поиск") },
                                modifier = Modifier.weight(1f).padding(end = 8.dp)
                            )
                            Button(
                                onClick = { scope.launch { drawerState.open() } },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedCategories.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Icon(imageVector = Icons.Default.FilterList, contentDescription = "Фильтр")
                                Text("Фильтр", modifier = Modifier.padding(start = 8.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(filteredCourses) { course ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            val courseId: String = course.courseId.toString()
                                            navController.navigate("coursesPreview/${userId}/${courseId}")
                                        }
                                        .padding(vertical = 8.dp),
                                    elevation = CardDefaults.cardElevation(2.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text("Название: ${course.nameCourse}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(4.dp))
                                        Text("Автор: ${course.fioOwner}", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(4.dp))
                                        Text("Категория: ${course.nameCategory}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(4.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
