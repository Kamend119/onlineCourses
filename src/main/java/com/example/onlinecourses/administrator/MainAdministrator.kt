package com.example.onlinecourses.administrator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.AppBarAdministrator
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

data class User(
    val firstName: String,
    val lastName: String,
    val isActive: Boolean
)

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainOwner() {
    val registeredUsers = 100 // Пример данных
    val activeUsers = 45 // Пример данных
    val users = listOf(
        User("Иван", "Иванов", true),
        User("Мария", "Петрова", false),
        User("Алексей", "Смирнов", true),
        User("Ольга", "Кузнецова", false)
    )

    var searchStartDate by remember { mutableStateOf("") }
    var searchEndDate by remember { mutableStateOf("") }
    var newUsers by remember { mutableStateOf(users) }

    OnlineCursesTheme {
        AppBarAdministrator(title = "Главная", showTopBar = true, showBottomBar = true) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Статистика пользователей
                item {
                    Text(
                        text = "Зарегистрированных пользователей: $registeredUsers",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Активных пользователей: $activeUsers",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Круговая диаграмма
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp) // Размер диаграммы
                            .padding(vertical = 16.dp)
                    ) {
                        CircularProgressIndicator(
                            progress = { activeUsers.toFloat() / registeredUsers },
                            modifier = Modifier.fillMaxSize(),
                            strokeWidth = 8.dp,
                            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                        )
                        Text(
                            text = "${(activeUsers.toFloat() / registeredUsers * 100).toInt()}%",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Новые пользователи за период
                item {
                    Text(
                        text = "Новые пользователи за период:",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = searchStartDate,
                            onValueChange = { searchStartDate = it },
                            label = { Text("Начало периода") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = searchEndDate,
                            onValueChange = { searchEndDate = it },
                            label = { Text("Конец периода") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Button(
                        onClick = {
                            // Логика поиска пользователей за период
                            newUsers = users.filter { it.firstName.startsWith("И") } // Пример фильтрации
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text("Поиск")
                    }
                }

                // Список пользователей
                items(newUsers) { user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(text = "Имя: ${user.firstName} ${user.lastName}")
                            Text(
                                text = "Статус: ${if (user.isActive) "Активен" else "Неактивен"}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                // Кнопка "Обращения в поддержку"
                item {
                    Button(
                        onClick = { /* Логика перехода к обращениям */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text("Обращения в поддержку")
                    }
                }
            }
        }
    }
}
