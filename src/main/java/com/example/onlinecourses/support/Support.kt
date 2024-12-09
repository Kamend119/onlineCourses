package com.example.onlinecourses.support

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.R
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

data class SupportRequest(
    val number: Int,
    val topic: String,
    val status: String
)

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Support() {
    val supportRequests = remember {
        listOf(
            SupportRequest(1, "Проблема с доступом к курсу", "В обработке"),
            SupportRequest(2, "Ошибка оплаты", "Закрыто"),
            SupportRequest(3, "Вопрос по заданию", "Открыто")
        )
    }

    OnlineCursesTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /* TODO: Add new request action */ },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Добавить",
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    "Поддержка",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(supportRequests) { request ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {

                                },
                            elevation = CardDefaults.cardElevation(2.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text("Обращение №${request.number}", style = MaterialTheme.typography.bodyMedium)
                                Text("Тема: ${request.topic}", style = MaterialTheme.typography.bodyMedium)
                                Text("Статус: ${request.status}", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}
