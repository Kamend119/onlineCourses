package com.example.onlinecourses.student.certificates

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.AppBarStudent
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

data class Certificate(
    val courseName: String,
    val courseAuthor: String
)

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainCertificate() {
    var searchQuery by remember { mutableStateOf("") }

    val certificates = listOf(
        Certificate("Курс Android Development", "Иван Иванов"),
        Certificate("Курс Web Development", "Мария Петрова"),
        Certificate("Курс Data Science", "Дмитрий Смирнов")
    )

    val filteredCertificates = certificates.filter {
        it.courseName.contains(searchQuery, ignoreCase = true) ||
                it.courseAuthor.contains(searchQuery, ignoreCase = true)
    }

    OnlineCursesTheme {
        AppBarStudent("Мои сертификаты", showTopBar = true, showBottomBar = true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Поиск") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredCertificates) { certificate ->
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
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Курс: ${certificate.courseName}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Автор: ${certificate.courseAuthor}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
