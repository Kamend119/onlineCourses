package com.example.onlinecourses.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.R
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Account() {
    val user = "Пользователь"

    OnlineCursesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Профиль",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Аватар",
                    modifier = Modifier.padding(16.dp).size(150.dp)
                )

                Text(
                    "Имя",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    "Фамилия",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    "Почта",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = {

                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Редактировать")
                }

                if (user == "Пользователь"){
                    Button(
                        onClick = {

                        },
                        modifier = Modifier.padding(bottom = 4.dp).fillMaxWidth()
                    ) {
                        Text("Отложенные курсы")
                    }
                    Button(
                        onClick = {

                        },
                        modifier = Modifier.padding(bottom = 4.dp).fillMaxWidth()
                    ) {
                        Text("Пройденные курсы")
                    }
                    Button(
                        onClick = {

                        },
                        modifier = Modifier.padding(bottom = 4.dp).fillMaxWidth()
                    ) {
                        Text("Мои сертификаты")
                    }
                }
            }
        }
    }
}