package com.example.onlinecourses.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Settings(){
    val user = "Студент"

    OnlineCursesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Настройки",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(
                    Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(MaterialTheme.colorScheme.primary)
                )

                Text(
                    text = "Профиль",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = {

                    },
                    Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Сменить пароль", style = MaterialTheme.typography.labelMedium)
                }

                if (user != "Администратор"){
                    Spacer(
                        Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )

                    Text(
                        text = "Поддержка",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = {

                        },
                        Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Обращения", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
    }
}