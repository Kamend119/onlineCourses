package com.example.onlinecourses.support

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlinecourses.network.SupportViewModel
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppeal(navController: NavHostController, userId: String) {
    val focusManager = LocalFocusManager.current
    val supportViewModel: SupportViewModel = viewModel()
    val supportSubjects by supportViewModel.supportSubjects
    val isLoadingSubjects by supportViewModel.isLoadingSubjects

    var expanded by remember { mutableStateOf(false) }
    var selectedTopic by remember { mutableStateOf("Выберите тему обращения") }
    var appealText by remember { mutableStateOf("") }
    var selectedSubjectId by remember { mutableStateOf(-1) }

    LaunchedEffect(Unit) {
        supportViewModel.loadSupportSubjects()
    }

    OnlineCursesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .padding(75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Создать обращение",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedTopic,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Тема обращения",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    supportSubjects.forEach { subject ->
                        DropdownMenuItem(
                            onClick = {
                                selectedTopic = subject.name
                                selectedSubjectId = subject.subjectId
                                expanded = false
                            },
                            text = { Text(subject.name) }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = appealText,
                onValueChange = { appealText = it },
                label = {
                    Text(
                        text = "Опишите проблему",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(MaterialTheme.colorScheme.surface)
            )

            Button(
                onClick = {
                    if (selectedSubjectId != -1 && appealText.isNotBlank()) {
                        supportViewModel.createSupportRequest(
                            userId.toInt(),
                            selectedSubjectId,
                            appealText
                        ) {
                            navController.navigate("support/$userId")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 25.dp),
                enabled = !isLoadingSubjects
            ) {
                Text("Создать")
            }

            if (isLoadingSubjects) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}

