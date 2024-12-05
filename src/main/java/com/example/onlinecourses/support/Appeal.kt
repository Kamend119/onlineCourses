package com.example.onlinecourses.support

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
fun Appeal(){
    val appealTopic = remember { "Техническая ошибка" }
    val appealStatus = remember { "В обработке" }
    val appealText = remember { "Не удается войти в приложение. Появляется ошибка 403." }
    val responseText = remember { "Ваше обращение принято. Мы разберемся с проблемой в течение 24 часов." }

    OnlineCursesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text("Поддержка", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 16.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = appealTopic,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Тема обращения") },
                    modifier = Modifier.padding(bottom = 8.dp),
                    textStyle = MaterialTheme.typography.bodyLarge
                )

                OutlinedTextField(
                    value = appealStatus,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Статус обращения") },
                    modifier = Modifier.padding(bottom = 16.dp),
                    textStyle = MaterialTheme.typography.bodyLarge
                )

                OutlinedTextField(
                    value = appealText,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Обращение") },
                    modifier = Modifier.padding(bottom = 16.dp),
                    textStyle = MaterialTheme.typography.bodyMedium
                )

                if (responseText.isNotEmpty()) {
                    OutlinedTextField(
                        value = responseText,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Ответ") },
                        modifier = Modifier.padding(bottom = 16.dp),
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}