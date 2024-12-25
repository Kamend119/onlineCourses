package com.example.onlinecourses.account

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.onlinecourses.network.EditAccountViewModel
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
fun EditAccount(navController: NavHostController, userId: String) {
    val viewModel: EditAccountViewModel = viewModel()
    var dateBirthday by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val focusManager = LocalFocusManager.current

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            dateBirthday = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val userDataState by viewModel.userData.collectAsState()

    LaunchedEffect(userId) {
        viewModel.fetchUserData(userId.toInt())
    }

    OnlineCursesTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            contentAlignment = Alignment.Center
        ) {

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
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Аватар",
                    modifier = Modifier.padding(16.dp).size(150.dp),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
                OutlinedTextField(
                    value = userDataState.firstName ?: "",
                    onValueChange = { viewModel.updateFirstName(it) },
                    label = {
                        Text(
                            text = "Имя",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    modifier = Modifier.padding(top = 16.dp)
                )

                OutlinedTextField(
                    value = userDataState.lastName ?: "",
                    onValueChange = { viewModel.updateLastName(it) },
                    label = {
                        Text(
                            text = "Фамилия",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    modifier = Modifier.padding(top = 16.dp)
                )

                OutlinedTextField(
                    value = userDataState.email ?: "",
                    onValueChange = { viewModel.updateEmail(it) },
                    label = {
                        Text(
                            text = "Почта",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    modifier = Modifier.padding(top = 16.dp)
                )

                OutlinedTextField(
                    value = userDataState.date_birth ?: "",
                    onValueChange = {},
                    label = {
                        Text(
                            text = "Дата рождения",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        ) },
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
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .background(MaterialTheme.colorScheme.surface)
                )

                Button(
                    onClick = {
                        val firstName = userDataState.firstName
                        val lastName = userDataState.lastName
                        val email = userDataState.email

                        Log.d("EditAccount", "FirstName: $firstName, LastName: $lastName, Email: $email")
                        if (lastName.isBlank()) {
                            Toast.makeText(context, "Фамилия не может быть пустой", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (firstName.isBlank()) {
                            Toast.makeText(context, "Имя не может быть пустым", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (email.isBlank()) {
                            Toast.makeText(context, "Почта не может быть пустой", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (dateBirthday.isBlank()) {
                            Toast.makeText(context, "Дата рождения не может быть пустой", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        viewModel.updateUserData(
                            userId = userId.toInt(),
                            email = email.trim(),
                            firstName = firstName.trim(),
                            lastName = lastName.trim(),
                            dateBirth = dateBirthday.trim()
                        )

                        Toast.makeText(context, "Данные успешно обновлены", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text(
                        text = "Сохранить",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}