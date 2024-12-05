package com.example.onlinecourses.entrance

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.R
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Registration(){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val roles = listOf("Студент", "Преподаватель", "Администратор")

    var dateBirthday by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            dateBirthday = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    OnlineCursesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text("Регистрация", style = MaterialTheme.typography.titleMedium)

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Почта", style = MaterialTheme.typography.bodyMedium) }
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Пароль", style = MaterialTheme.typography.bodyMedium) }
                )

                OutlinedTextField(
                    value = repeatPassword,
                    onValueChange = { repeatPassword = it },
                    label = { Text("Повторите пароль", style = MaterialTheme.typography.bodyMedium) }
                )

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("Имя", style = MaterialTheme.typography.bodyMedium) }
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Фамилия", style = MaterialTheme.typography.bodyMedium) }
                )

                @OptIn(ExperimentalMaterial3Api::class)
                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = role,
                        onValueChange = { role = it },
                        label = { Text("Роль", style = MaterialTheme.typography.bodyMedium) },
                        readOnly = true,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false }
                    ) {
                        roles.forEach { selectedRole ->
                            DropdownMenuItem(
                                text = { Text(selectedRole, style = MaterialTheme.typography.bodyMedium) },
                                onClick = {
                                    role = selectedRole
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = dateBirthday,
                    onValueChange = {},
                    label = { Text("Дата рождения", style = MaterialTheme.typography.bodyMedium) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.calendar),
                                contentDescription = "Выберите дату",
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                )

                Button(
                    onClick = {

                    },
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Text("Зарегистрироваться", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}