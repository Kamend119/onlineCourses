package com.example.onlinecourses.administrator.support

import com.example.onlinecourses.AppBarAdministrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Appeal() {
    val appealTopic = remember { "Техническая ошибка" }
    val appealStatus = remember { "В обработке" }
    val appealText = remember { "Не удается войти в приложение. Появляется ошибка 403." }
    var responseText by remember { mutableStateOf("") }

    OnlineCursesTheme {
        AppBarAdministrator("Поддержка", true, true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    item{
                        OutlinedTextField(
                            value = appealTopic,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Тема обращения") },
                            modifier = Modifier.padding(20.dp, 8.dp).fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyLarge
                        )
                        OutlinedTextField(
                            value = appealStatus,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Статус обращения") },
                            modifier = Modifier.padding(20.dp, 8.dp).fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyLarge
                        )

                        OutlinedTextField(
                            value = appealText,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Обращение") },
                            modifier = Modifier.padding(20.dp, 8.dp).fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyLarge
                        )

                        OutlinedTextField(
                            value = responseText,
                            onValueChange = { responseText = it },
                            label = { Text("Добавить ответ") },
                            modifier = Modifier.padding(20.dp, 8.dp).fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Button(
                    onClick = {
                        println("Ответ сохранен: $responseText")
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Сохранить")
                }
            }
        }
    }
}
