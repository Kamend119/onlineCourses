package com.example.onlinecourses.student.searchCourses

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlinecourses.AppBarStudent
import com.example.onlinecourses.network.CourseViewModelCreateStudentCourse
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
fun CoursesPreview(navController: NavHostController, userId: String, courseId: String) {
    val context = LocalContext.current
    val courseViewModel: CourseViewModelCreateStudentCourse = viewModel()
    val courseIdInt = courseId.toInt()
    val focusManager = LocalFocusManager.current
    val message = courseViewModel.message

    LaunchedEffect(courseIdInt) {
        courseViewModel.getCourseDetails(courseIdInt)
    }

    LaunchedEffect(message) {
        if (message.contains("Update!!!")) {
            Toast.makeText(context, "Поздравляем с поступлением на курс!", Toast.LENGTH_SHORT).show()
        }
    }

    OnlineCursesTheme {
        AppBarStudent("Просмотр курса", showTopBar = true, showBottomBar = true, navController, userId) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (courseViewModel.isLoading.value) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Загрузка...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = {
                                    focusManager.clearFocus()
                                })
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = courseViewModel.courseName,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = courseViewModel.courseDescription,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(8.dp)
                        )

                        Row(
                            Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    courseViewModel.enrollInCourse(userId.toInt(), courseIdInt)
                                },
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text("Поступить")
                            }
                        }
                    }
                }
            }
        }
    }
}