package com.example.onlinecourses.entrance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
fun Entrance(){
    OnlineCursesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text("Вход", style = MaterialTheme.typography.titleMedium)

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {

                    }
                ) {
                    Text("Авторизация", style = MaterialTheme.typography.labelMedium)
                }

                Button(
                    onClick = {

                    }
                ) {
                    Text("Регистрация", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}