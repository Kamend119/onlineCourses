package com.example.onlinecourses.courseOwner.editCourse

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.AppBarCourseOwner
import com.example.onlinecourses.ui.theme.OnlineCursesTheme
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun StepViewPreview() {
    val stepData = StepData(
        question = "Введите вопрос?",
        type = "file",
        textAnswer = null,
        options = mutableListOf("Вариант 1" to false, "Вариант 2" to true),
        fileUri = Uri.parse("content://example.com/file.pdf")
    )
    StepView(stepData)
}

fun downloadFile(context: Context, fileUri: Uri) {
    val contentResolver: ContentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(fileUri)

    inputStream?.let { input ->
        val file = File(context.getExternalFilesDir(DIRECTORY_DOWNLOADS), fileUri.lastPathSegment ?: "file.pdf")
        val outputStream: OutputStream = FileOutputStream(file)

        val buffer = ByteArray(1024)
        var length: Int
        while (input.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }

        outputStream.flush()
        input.close()
        outputStream.close()
    }
}

@Composable
fun StepView(stepData: StepData) {
    val questionText = remember { stepData.question }
    val mode = remember { stepData.type }
    val textAnswer = remember { stepData.textAnswer ?: "" }
    val answerOptions = remember { stepData.options ?: mutableListOf() }
    val selectedFileUri = remember { stepData.fileUri }

    val context = LocalContext.current // Получаем контекст

    OnlineCursesTheme {
        AppBarCourseOwner(title = "Проверка шага", showTopBar = true, showBottomBar = true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = questionText,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Текст вопроса") },
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
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Текст ответа") },
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
                                            onCheckedChange = {},
                                            enabled = false
                                        )
                                        Text(
                                            text = text,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                }
                            }
                        }

                        "file" -> {
                            selectedFileUri?.let {
                                Text(
                                    "Выбранный файл: ${it.lastPathSegment ?: "Неизвестное имя файла"}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                // Добавляем кнопку для скачивания файла
                                Button(
                                    onClick = {
                                        downloadFile(context = context, fileUri = it) // Передаем context
                                    },
                                    modifier = Modifier.padding(top = 10.dp)
                                ) {
                                    Text("Скачать файл")
                                }
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
                        println("Шаг удалён: $stepData")
                    },
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text("Удалить шаг")
                }
            }
        }
    }
}
