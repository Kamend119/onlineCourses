package com.example.onlinecourses.student.takingCourses

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlinecourses.AppBarStudent
import com.example.onlinecourses.network.CourseViewModelMainCourses
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
fun MainCourses(navController: NavHostController, userId: String, courseId: String) {
    val viewModel: CourseViewModelMainCourses = viewModel()
    val context = LocalContext.current
    val coursesName = viewModel.course?.nameCourse ?: ""
    val coursesDescription = viewModel.course?.description ?: ""
    val corsesOwner = viewModel.course?.fioOwner ?: ""

    OnlineCursesTheme {
        AppBarStudent(title = "Прохождение курса", showTopBar = true, showBottomBar = true, navController, userId) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = coursesName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = coursesDescription,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(8.dp)
                    )

                    Text(
                        text = "Автор: $corsesOwner",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(viewModel.stepsList) { step ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val stepId = step.stepId.toString()
                                        when (step.nameType) {
                                            "Лекция" -> {
                                                navController.navigate("lecture/${userId}/${stepId}")
                                            }
                                            "Вопрос" -> {
                                                navController.navigate("question/${userId}/${stepId}")
                                            }
                                            else -> {
                                                Toast.makeText(context, "Шаг не найден", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(2.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "Тип шага: ${step.nameType}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Номер шага: ${step.stepId}",
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

    LaunchedEffect(courseId) {
        viewModel.loadCourse(courseId.toInt()) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
        viewModel.loadSteps(courseId.toInt()) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}
