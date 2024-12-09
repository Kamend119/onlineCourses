package com.example.onlinecourses.courseOwner.createCourse

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
import com.example.onlinecourses.AppBarCourseOwner
import com.example.onlinecourses.R
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainCreate() {
    var coursesName by remember { mutableStateOf("") }
    var coursesDescription by remember { mutableStateOf("") }

    OnlineCursesTheme {
        AppBarCourseOwner(title = "Создание курса", showTopBar = true, showBottomBar = true) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    OutlinedTextField(
                        value = coursesName,
                        onValueChange = { coursesName = it },
                        label = { Text("Наименование") },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = coursesDescription,
                        onValueChange = { coursesDescription = it },
                        label = { Text("Описание") },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            // Обработка сохранения
                        }
                    ) {
                        Text("Сохранить")
                    }
                }

                FloatingActionButton(
                    onClick = { /* TODO: Add new request action */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Добавить",
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }
    }
}
