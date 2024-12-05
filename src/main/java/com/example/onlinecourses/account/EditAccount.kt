package com.example.onlinecourses.account

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.R
import com.example.onlinecourses.ui.theme.OnlineCursesTheme
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.platform.LocalContext

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun EditAccount() {
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
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

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Имя", style = MaterialTheme.typography.bodyMedium) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("Фамилия", style = MaterialTheme.typography.bodyMedium) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

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
                    },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                Button(
                    onClick = {

                    }
                ) {
                    Text("Сохранить")
                }
            }
        }
    }
}