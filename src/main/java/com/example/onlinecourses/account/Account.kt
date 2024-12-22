package com.example.onlinecourses.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlinecourses.R
import com.example.onlinecourses.network.AccountViewModel
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
fun Account(navController: NavHostController, userId: String, role: String) {
    val viewModel: AccountViewModel = viewModel()
    val userDataState by viewModel.userData.collectAsState()
    LaunchedEffect(userId) {
        viewModel.fetchUserData(userId.toInt())
    }

    OnlineCursesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Профиль",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (userDataState.isLoading) {
                Text(
                    text = "Загрузка данных...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else if (userDataState.error != null) {
                Text(
                    text = "Ошибка: ${userDataState.error}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Аватар",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(150.dp),
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )

                    Text(
                        "Имя: ${userDataState.firstName}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        "Фамилия: ${userDataState.lastName}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        "Почта: ${userDataState.email}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = {
                            navController.navigate("editAccount/${userId}")
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text(
                            "Редактировать",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    if (role == "Студент") {
                        Button(
                            onClick = {
                                navController.navigate("deferredCourses/${userId}")
                            },
                            modifier = Modifier
                                .padding(bottom = 4.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                "Отложенные курсы",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Button(
                            onClick = {
                                navController.navigate("completedCourses/${userId}")
                            },
                            modifier = Modifier
                                .padding(bottom = 4.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                "Пройденные курсы",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Button(
                            onClick = {
                                navController.navigate("certificate/${userId}")
                            },
                            modifier = Modifier
                                .padding(bottom = 4.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                "Мои сертификаты",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}