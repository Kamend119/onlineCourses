package com.example.onlinecourses.support

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onlinecourses.network.SupportViewModel
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
fun Appeal(userId: String, subjectId: String) {
    val viewModel: SupportViewModel = viewModel()
    LaunchedEffect(userId) {
        viewModel.getSupportRequests(userId)
    }
    val supportRequests by viewModel.supportRequests
    val subjectIdInt = subjectId.toIntOrNull()
    val request = if (subjectIdInt != null) {
        supportRequests.find { it.request_id == subjectIdInt }
    } else {
        null
    }

    OnlineCursesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                "Поддержка",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            if (request != null) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    OutlinedTextField(
                        value = request.subject_name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Тема обращения") },
                        modifier = Modifier.padding(bottom = 8.dp),
                        textStyle = MaterialTheme.typography.bodyLarge
                    )

                    OutlinedTextField(
                        value = request.status,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Статус обращения") },
                        modifier = Modifier.padding(bottom = 16.dp),
                        textStyle = MaterialTheme.typography.bodyLarge
                    )

                    OutlinedTextField(
                        value = request.message,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Обращение") },
                        modifier = Modifier.padding(bottom = 16.dp),
                        textStyle = MaterialTheme.typography.bodyMedium
                    )

                    if (request.admin_answer != null) {
                        OutlinedTextField(
                            value = request.admin_answer,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Ответ") },
                            modifier = Modifier.padding(bottom = 16.dp),
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else {
                // Если request не найден
                Text("Обращение не найдено", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
