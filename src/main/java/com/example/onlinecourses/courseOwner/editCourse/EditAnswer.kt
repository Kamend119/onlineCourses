package com.example.onlinecourses.courseOwner.editCourse

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.AppBarCourseOwner
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

data class StepData(
    var question: String,
    var type: String,
    var textAnswer: String?,
    var options: MutableList<Pair<String, Boolean>>?,
    var fileUri: Uri?
)

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun EditStepPreview() {
    val stepData = StepData(
        question = "Введите вопрос?",
        type = "options",
        textAnswer = null,
        options = mutableListOf("Вариант 1" to false, "Вариант 2" to true),
        fileUri = null
    )
    EditStep(stepData)
}

@Composable
fun EditStep(stepData: StepData) {
    var mode by remember { mutableStateOf(stepData.type) } // Текущий режим
    var questionText by remember { mutableStateOf(stepData.question) }
    var textAnswer by remember { mutableStateOf(stepData.textAnswer ?: "") }
    val answerOptions = remember { stepData.options ?: mutableListOf() }
    var optionText by remember { mutableStateOf("") }

    // Для выбора файла
    var selectedFileUri by remember { mutableStateOf(stepData.fileUri) }
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> selectedFileUri = uri }

    OnlineCursesTheme {
        AppBarCourseOwner(title = "Редактирование шага", showTopBar = true, showBottomBar = true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = questionText,
                    onValueChange = { questionText = it },
                    label = { Text("Редактировать текст вопроса") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Column(
                    Modifier.weight(1f)
                ) {
                    when (mode) {
                        "text" -> {
                            OutlinedTextField(
                                value = textAnswer,
                                onValueChange = { textAnswer = it },
                                label = { Text("Редактировать текст ответа") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            )
                        }

                        "options" -> {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            ) {
                                items(answerOptions) { (text, isChecked) ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    ) {
                                        Checkbox(
                                            checked = isChecked,
                                            onCheckedChange = { isChecked ->
                                                val index =
                                                    answerOptions.indexOfFirst { it.first == text }
                                                if (index != -1) {
                                                    answerOptions[index] = text to isChecked
                                                }
                                            }
                                        )
                                        Text(
                                            text = text,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                }
                                item {
                                    OutlinedTextField(
                                        value = optionText,
                                        onValueChange = { optionText = it },
                                        label = { Text("Добавить новый вариант ответа") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp)
                                    )

                                    Button(
                                        onClick = {
                                            if (optionText.isNotEmpty()) {
                                                answerOptions.add(optionText to false)
                                                optionText = ""
                                            }
                                        },
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    ) {
                                        Text("Добавить вариант")
                                    }
                                }
                            }
                        }

                        "file" -> {
                            Button(
                                onClick = { filePickerLauncher.launch("*/*") },
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                Text("Выбрать файл")
                            }
                            selectedFileUri?.let {
                                Text(
                                    "Выбранный файл: $it",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            } ?: Text(
                                "Файл не выбран",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        else -> {
                            Text("Неизвестный тип шага", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Button(
                    onClick = {
                        stepData.question = questionText
                        stepData.type = mode
                        stepData.textAnswer = if (mode == "text") textAnswer else null
                        stepData.options = if (mode == "options") answerOptions else null
                        stepData.fileUri = if (mode == "file") selectedFileUri else null

                        println("Сохранение шага: $stepData")
                    },
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text("Сохранить изменения")
                }
            }
        }
    }
}
