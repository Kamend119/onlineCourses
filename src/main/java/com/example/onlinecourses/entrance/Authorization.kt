package com.example.onlinecourses.entrance

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlinecourses.network.AuthorizationViewModel
import com.example.onlinecourses.ui.theme.OnlineCursesTheme
import com.example.onlinecourses.ui.theme.rememberDarkModeStateSystem

@Composable
fun Authorization(navController: NavHostController) {
    val viewModel: AuthorizationViewModel = viewModel()

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isLoading = viewModel.isLoading
    val context = LocalContext.current

    val isDarkMode = rememberDarkModeStateSystem()

    OnlineCursesTheme(darkTheme = isDarkMode) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background) // Устанавливаем фон из цветовой схемы
                .padding(75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Авторизация",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground // Цвет текста на фоне
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                if (isLoading.value) {
                    Text(
                        text = "Загрузка...",
                        modifier = Modifier.padding(top = 16.dp),
                        color = MaterialTheme.colorScheme.onBackground // Цвет текста загрузки
                    )
                } else {
                    OutlinedTextField(
                        value = login,
                        onValueChange = { login = it },
                        label = {
                            Text(
                                text = "Логин",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground // Цвет текста метки
                            )
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .background(MaterialTheme.colorScheme.surface) // Цвет поверхности
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text(
                                text = "Пароль",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground // Цвет текста метки
                            )
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .background(MaterialTheme.colorScheme.surface) // Цвет поверхности
                    )

                    Button(
                        onClick = {
                            if (login.isBlank()) {
                                Toast.makeText(context, "Логин не может быть пустым", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (password.isBlank()) {
                                Toast.makeText(context, "Пароль не может быть пустым", Toast.LENGTH_SHORT).show()
                                return@Button
                            } else {
                                viewModel.login(login, password) { userId, roleName ->
                                    if (roleName == "Админ") {
                                        navController.navigate("mainAdministrator/${userId}")
                                        Toast.makeText(context, "Добро пожаловать, Админ!", Toast.LENGTH_SHORT).show()
                                    } else if (roleName == "Владелец") {
                                        navController.navigate("mainOwner/${userId}")
                                        Toast.makeText(context, "Добро пожаловать, Владелец!", Toast.LENGTH_SHORT).show()
                                    } else if (roleName == "Студент") {
                                        navController.navigate("mainStudent/${userId}")
                                        Toast.makeText(context, "Добро пожаловать, Студент!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Роль не распознана", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        Text(
                            text = "Войти",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimary // Цвет текста на кнопке
                        )
                    }
                }
            }
        }
    }
}
