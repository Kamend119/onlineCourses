package com.example.onlinecourses.support

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
                        Column(
                            Modifier
                                .padding(5.dp)
                                .clickable {  }
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(16.dp))
                                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                                .padding(16.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
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