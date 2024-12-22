package com.example.onlinecourses.entrance

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlinecourses.R
import com.example.onlinecourses.functions.isEmailValid
import com.example.onlinecourses.functions.isPasswordValid
import com.example.onlinecourses.functions.isValidAge
import com.example.onlinecourses.network.RegistrationViewModel
import com.example.onlinecourses.network.User
import com.example.onlinecourses.ui.theme.OnlineCursesTheme
import com.example.onlinecourses.ui.theme.rememberDarkModeStateSystem
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registration(
    navController: NavHostController
) {
    val viewModel: RegistrationViewModel = viewModel()
    val focusManager = LocalFocusManager.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var dateBirthday by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }.time
            dateBirthday = dateFormat.format(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val roles = viewModel.roles
    val isLoading = viewModel.isLoading

    LaunchedEffect(Unit) {
        viewModel.loadRoles()
    }

    val isDarkMode = rememberDarkModeStateSystem()

    OnlineCursesTheme(darkTheme = isDarkMode) {
        LazyColumn(
            modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(75.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Text(
                    text = "Регистрация",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground // Цвет текста на фоне
                )

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = {
                                Text(
                                    text = "Почта",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground // Цвет текста метки
                                )
                            },
                            modifier = Modifier
                                .padding(top = 8.dp)
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
                                .padding(top = 8.dp)
                                .background(MaterialTheme.colorScheme.surface) // Цвет поверхности
                        )

                        OutlinedTextField(
                            value = repeatPassword,
                            onValueChange = { repeatPassword = it },
                            label = {
                                Text(
                                    text = "Повторите пароль",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground // Цвет текста метки
                                )
                            },
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .background(MaterialTheme.colorScheme.surface) // Цвет поверхности
                        )

                        OutlinedTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            label = {
                                Text(
                                    text = "Имя",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground // Цвет текста метки
                                )
                            },
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .background(MaterialTheme.colorScheme.surface) // Цвет поверхности
                        )

                        OutlinedTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = {
                                Text(
                                    text = "Фамилия",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground // Цвет текста метки
                                )
                            },
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .background(MaterialTheme.colorScheme.surface) // Цвет поверхности
                        )

                        ExposedDropdownMenuBox(
                            expanded = isDropdownExpanded,
                            onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
                        ) {
                            OutlinedTextField(
                                value = role,
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text(
                                        text = "Роль",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onBackground // Цвет текста метки
                                    )
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = isDropdownExpanded
                                    )
                                },
                                modifier = Modifier.menuAnchor().padding(top = 8.dp),
                                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = isDropdownExpanded,
                                onDismissRequest = { isDropdownExpanded = false }
                            ) {
                                roles.forEach { selectedRole ->
                                    DropdownMenuItem(
                                        text = { Text(selectedRole.nameRole) },
                                        onClick = {
                                            role = selectedRole.nameRole
                                            isDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = dateBirthday,
                            onValueChange = {},
                            label = {
                                Text(
                                    text = "Дата рождения",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground // Цвет текста метки
                                )
                            },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = { datePickerDialog.show() }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.calendar),
                                        contentDescription = "Выберите дату",
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                            },
                            modifier = Modifier.padding(top = 8.dp),
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        )

                        Button(
                            onClick = {
                                if (email.isBlank() || password.isBlank() || repeatPassword.isBlank() || firstName.isBlank() || lastName.isBlank() || role.isBlank() || dateBirthday.isBlank()) {
                                    Toast.makeText(context, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                if (!isEmailValid(email)) {
                                    Toast.makeText(context, "Введите корректную почту", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                if (!isPasswordValid(password)) {
                                    Toast.makeText(
                                        context,
                                        "Пароль должен содержать не менее 8 символов, включая строчные и заглавные буквы, а также хотя бы одну цифру",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return@Button
                                }

                                if (password != repeatPassword) {
                                    Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                if (!isValidAge(dateBirthday)) {
                                    Toast.makeText(context, "Вам должно быть не менее 14 лет", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                val selectedRoleId = roles.find { it.nameRole == role }?.roleId ?: -1

                                val user = User(
                                    email = email,
                                    login = email,
                                    password = password,
                                    firstName = firstName,
                                    lastName = lastName,
                                    roleId = selectedRoleId,
                                    gender = "Не указано",
                                    dataBirth = dateBirthday
                                )
                                viewModel.registerUser(user) {
                                    navController.navigate("entrance")
                                }
                            },
                            modifier = Modifier.padding(top = 20.dp)
                        ) {
                            Text(
                                text = "Зарегистрироваться",
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