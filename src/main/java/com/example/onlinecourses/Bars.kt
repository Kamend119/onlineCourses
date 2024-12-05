package com.example.onlinecourses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.onlinecourses.ui.theme.OnlineCursesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarStudent(
    title: String,
    showTopBar: Boolean = true,
    showBottomBar: Boolean = true,
    onTopBarIconClick: (String) -> Unit = {},
    onBottomBarIconClick: (String) -> Unit = {},
    content: @Composable () -> Unit
) {
    OnlineCursesTheme {
        Scaffold(
            topBar = {
                if (showTopBar) {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {},
                        actions = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    title,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Row {
                                    IconButton(onClick = { onTopBarIconClick("account") }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.user),
                                            contentDescription = "Акаунт",
                                            modifier = Modifier.size(35.dp)
                                        )
                                    }
                                    IconButton(onClick = { onTopBarIconClick("setting") }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.settings),
                                            contentDescription = "Настройки",
                                            modifier = Modifier.size(35.dp)
                                        )
                                    }
                                }
                            }
                        },
                        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                    )
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 75.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                    ) {
                        androidx.compose.material3.Surface(
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                            shadowElevation = 8.dp,
                            tonalElevation = 4.dp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { onBottomBarIconClick("search") }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.search),
                                        contentDescription = "Поиск",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                                IconButton(onClick = { onBottomBarIconClick("home") }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.home),
                                        contentDescription = "Главная",
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                                IconButton(onClick = { onBottomBarIconClick("courses") }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.book),
                                        contentDescription = "Мои курсы",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarCourseOwner(
    title: String,
    showTopBar: Boolean = true,
    showBottomBar: Boolean = true,
    onTopBarIconClick: (String) -> Unit = {},
    onBottomBarIconClick: (String) -> Unit = {},
    content: @Composable () -> Unit
) {
    OnlineCursesTheme {
        Scaffold(
            topBar = {
                if (showTopBar) {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {},
                        actions = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    title,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Row {
                                    IconButton(onClick = { onTopBarIconClick("account") }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.user),
                                            contentDescription = "Акаунт",
                                            modifier = Modifier.size(35.dp)
                                        )
                                    }
                                    IconButton(onClick = { onTopBarIconClick("setting") }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.settings),
                                            contentDescription = "Настройки",
                                            modifier = Modifier.size(35.dp)
                                        )
                                    }
                                }
                            }
                        },
                        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                    )
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 75.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                    ) {
                        androidx.compose.material3.Surface(
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                            shadowElevation = 8.dp,
                            tonalElevation = 4.dp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { onBottomBarIconClick("courses") }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.book),
                                        contentDescription = "Мои курсы",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                                IconButton(onClick = { onBottomBarIconClick("home") }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.home),
                                        contentDescription = "Главная",
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                                IconButton(onClick = { onBottomBarIconClick("statistic") }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.graph),
                                        contentDescription = "Статистика",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarAdministrator(
    title: String,
    showTopBar: Boolean = true,
    showBottomBar: Boolean = true,
    onTopBarIconClick: (String) -> Unit = {},
    onBottomBarIconClick: (String) -> Unit = {},
    content: @Composable () -> Unit
) {
    OnlineCursesTheme {
        Scaffold(
            topBar = {
                if (showTopBar) {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {},
                        actions = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    title,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Row {
                                    IconButton(onClick = { onTopBarIconClick("account") }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.user),
                                            contentDescription = "Акаунт",
                                            modifier = Modifier.size(35.dp)
                                        )
                                    }
                                    IconButton(onClick = { onTopBarIconClick("setting") }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.settings),
                                            contentDescription = "Настройки",
                                            modifier = Modifier.size(35.dp)
                                        )
                                    }
                                }
                            }
                        },
                        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                    )
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 75.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                    ) {
                        androidx.compose.material3.Surface(
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                            shadowElevation = 8.dp,
                            tonalElevation = 4.dp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { onBottomBarIconClick("courses") }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.book),
                                        contentDescription = "Мои курсы",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                                IconButton(onClick = { onBottomBarIconClick("home") }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.home),
                                        contentDescription = "Главная",
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                                IconButton(onClick = { onBottomBarIconClick("users") }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.user),
                                        contentDescription = "Пользователи",
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    }
}
