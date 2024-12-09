package com.example.onlinecourses.courseOwner.createCourse

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

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CreateAnswerPreview() {
    CreateAnswer()
}

@Composable
fun CreateAnswer() {
    var mode by remember { mutableStateOf("default") } // Текущий режим страницы
    var questionText by remember { mutableStateOf("") } // Текст вопроса

    // Для добавления текстового ответа
    var textAnswer by remember { mutableStateOf("") }

    // Для добавления вариантов ответа
    val answerOptions = remember { mutableStateListOf<Pair<String, Boolean>>() }
    var optionText by remember { mutableStateOf("") }

    // Для выбора файла
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedFileUri = uri
    }

    OnlineCursesTheme {
        AppBarCourseOwner(title = "Добавить вопрос", showTopBar = true, showBottomBar = true) {
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
                    label = { Text("Введите текст вопроса") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Column(
                    Modifier.weight(1f)
                ){
                    when (mode) {
                        "textAnswer" -> {
                            OutlinedTextField(
                                value = textAnswer,
                                onValueChange = { textAnswer = it },
                                label = { Text("Введите текст ответа") },
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
                                        label = { Text("Введите текст варианта ответа") },
                                        modifier = Modifier
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
                            Button(
                                onClick = { mode = "textAnswer" },
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text("Добавить текстовый ответ")
                            }
                            Button(
                                onClick = { mode = "options" },
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text("Добавить варианты ответов")
                            }
                            Button(
                                onClick = { mode = "file" },
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text("Добавить файл")
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        // Обработчик сохранения
                    },
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text("Сохранить")
                }
            }
        }
    }
}
