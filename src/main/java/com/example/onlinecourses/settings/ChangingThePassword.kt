package com.example.onlinecourses.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlinecourses.functions.isPasswordValid
import com.example.onlinecourses.network.ChangePasswordViewModel
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
fun ChangingThePassword(navController: NavHostController, userId: String, role: String) {
    val viewModel: ChangePasswordViewModel = viewModel()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var newPassword by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    val errorMessage = viewModel.errorMessage
    val isLoading = viewModel.isLoading
    val successMessage = viewModel.successMessage

    viewModel.onPasswordChanged = {
        navController.navigate("settings")
    }

    OnlineCursesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(50.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Настройки",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Смена пароля",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = {
                        Text(
                            text = "Новый пароль",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .background(MaterialTheme.colorScheme.surface)
                )

                OutlinedTextField(
                    value = repeatPassword,
                    onValueChange = { repeatPassword = it },
                    label = {
                        Text(
                            text = "Повторите пароль",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .background(MaterialTheme.colorScheme.surface)
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                if (successMessage.isNotEmpty()) {
                    Text(
                        text = successMessage,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        if (!isPasswordValid(newPassword)) {
                            Toast.makeText(
                                context,
                                "Пароль должен содержать не менее 8 символов, включая строчные и заглавные буквы, а также хотя бы одну цифру",
                                Toast.LENGTH_LONG
                            ).show()
                            return@Button
                        }

                        if (newPassword != repeatPassword) {
                            Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        viewModel.changePassword(userId.toInt(), newPassword, repeatPassword)
                        Toast.makeText(context, "Пароль изменен", Toast.LENGTH_SHORT).show()
                        navController.navigate("settings/${userId}/${role}")
                    },
                    modifier = Modifier.padding(top = 20.dp).align(Alignment.CenterHorizontally)
                ) {
                    Text("Сохранить", style = MaterialTheme.typography.labelMedium)
                }

                if (isLoading) {
                    Text(
                        text = "Загрузка...", style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}
