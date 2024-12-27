package com.example.onlinecourses.student.takingCourses

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlinecourses.AppBarStudent
import com.example.onlinecourses.network.QuestionViewModel
import com.example.onlinecourses.ui.theme.OnlineCursesTheme


@Composable
fun Question(navController: NavHostController, userId: String, stepId: String) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val viewModel: QuestionViewModel = viewModel()
    val stepData by viewModel.stepData.collectAsState()
    val userAnswer by viewModel.userAnswer.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var textAnswer by remember { mutableStateOf("") }
    val answerOptionsState = remember { mutableStateListOf<Pair<String, Boolean>>() }

    LaunchedEffect(Unit) {
        viewModel.loadStepData(stepId.toInt(), userId.toInt())
    }

    LaunchedEffect(stepData, userAnswer) {
        Log.d("QuestionScreen", "stepData: $stepData")
        Log.d("QuestionScreen", "userAnswer: $userAnswer")
        if (userAnswer != null) {
            Log.d("QuestionScreen", "Answer Text: ${userAnswer!!.answer_text}")
        }
        if (stepData?.answerOptions.isNullOrEmpty()) {
            textAnswer = userAnswer?.answer_text?.joinToString(" ") ?: ""
        } else {
            answerOptionsState.clear()
            stepData?.answerOptions?.forEach { option ->
                val isChecked = userAnswer?.answer_text?.contains(option) == true
                answerOptionsState.add(option to isChecked)
            }
        }
    }

    OnlineCursesTheme {
        AppBarStudent(title = "Ответ на шаг", showTopBar = true, showBottomBar = true, navController, userId) {
            when {
                isLoading -> {
                    Text(
                        "Загрузка...",
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                errorMessage != null -> {
                    Text(
                        "Ошибка: $errorMessage",
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = { focusManager.clearFocus() })
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        OutlinedTextField(
                            value = stepData?.questionText.orEmpty(),
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Текст вопроса") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )

                        if (userAnswer?.estimation != null && userAnswer?.estimation != 0) {
                            Text(
                                "Оценка: ${userAnswer!!.estimation}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(4.dp)
                            )
                            Text(
                                "Комментарий: ${userAnswer!!.comment}",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(4.dp)
                            )
                        } else {
                            Column(Modifier.weight(1f)) {
                                if (stepData?.answerOptions.isNullOrEmpty()) {
                                    OutlinedTextField(
                                        value = textAnswer,
                                        onValueChange = { textAnswer = it },
                                        label = { Text("Ваш ответ") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp)
                                    )
                                } else {
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp)
                                    ) {
                                        items(stepData?.answerOptions.orEmpty()) { option ->
                                            val isChecked = answerOptionsState.firstOrNull { it.first == option }?.second ?: false
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            ) {
                                                Checkbox(
                                                    checked = isChecked,
                                                    onCheckedChange = { checked ->
                                                        val index = answerOptionsState.indexOfFirst { it.first == option }
                                                        if (index != -1) {
                                                            answerOptionsState[index] = option to checked
                                                        }
                                                    }
                                                )
                                                Text(
                                                    text = option,
                                                    modifier = Modifier.padding(start = 8.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Button(
                                onClick = {
                                    val finalAnswerArray = if (stepData?.answerOptions.isNullOrEmpty()) {
                                        listOf(textAnswer.trim())
                                    } else {
                                        answerOptionsState.filter { it.second }.map { it.first }
                                    }

                                    val formattedAnswer = finalAnswerArray.joinToString(
                                        prefix = "{",
                                        postfix = "}",
                                        separator = ","
                                    ) { "\"$it\"" }

                                    viewModel.submitAnswer(
                                        userId = userId.toInt(),
                                        stepId = stepId.toInt(),
                                        answerText = formattedAnswer,
                                        fileUri = null,
                                        context = context
                                    )
                                },
                                modifier = Modifier.padding(top = 10.dp),
                                enabled = userAnswer?.estimation == null || userAnswer?.estimation == 0
                            ) {
                                Text("Сохранить")
                            }
                        }
                    }
                }
            }
        }
    }
}