package com.example.onlinecourses.student.takingCourses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlinecourses.AppBarStudent
import com.example.onlinecourses.network.LectureViewModel
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
fun Lecture(navController: NavHostController, userId: String, stepId: String) {
    val viewModel: LectureViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLecture(stepId.toInt())
    }

    OnlineCursesTheme {
        AppBarStudent(title = "Лекция", showTopBar = true, showBottomBar = true, navController, userId) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                when {
                    uiState.isLoading -> {
                        Text(text = "Загрузка...", modifier = Modifier.padding(16.dp))
                    }
                    uiState.errorMessage != null -> {
                        Text(
                            text = "Ошибка: ${uiState.errorMessage}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    uiState.lectureText != null -> {
                        Text(
                            text = uiState.lectureText!!,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    else -> {
                        Text(text = "Лекция не найдена.", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}
