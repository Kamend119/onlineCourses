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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
fun Entrance(navController: NavHostController){
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
                        navController.navigate("authorization")
                    }
                ) {
                    Text("Авторизация", style = MaterialTheme.typography.labelMedium)
                }

                Button(
                    onClick = {
                        navController.navigate("registration")
                    }
                ) {
                    Text("Регистрация", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}