package com.example.onlinecourses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onlinecourses.account.Account
import com.example.onlinecourses.account.EditAccount
import com.example.onlinecourses.entrance.Authorization
import com.example.onlinecourses.entrance.Entrance
import com.example.onlinecourses.entrance.Registration
import com.example.onlinecourses.settings.ChangingThePassword
import com.example.onlinecourses.settings.Settings
import com.example.onlinecourses.student.CompletedCourses
import com.example.onlinecourses.student.DeferredCourses
import com.example.onlinecourses.student.MainStudent
import com.example.onlinecourses.student.certificates.Certificate
import com.example.onlinecourses.student.certificates.MainCertificate
import com.example.onlinecourses.student.myCourses.MainMyCourses
import com.example.onlinecourses.student.searchCourses.CoursesPreview
import com.example.onlinecourses.student.searchCourses.MainSearchCourses
import com.example.onlinecourses.student.takingCourses.Lecture
import com.example.onlinecourses.student.takingCourses.MainCourses
import com.example.onlinecourses.student.takingCourses.Question
import com.example.onlinecourses.support.AddAppeal
import com.example.onlinecourses.support.Appeal
import com.example.onlinecourses.support.Support


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

//  navController.navigate("authorization")

@Composable
fun MyApp() {
    val navController = rememberNavController()
    var userId by remember { mutableStateOf("1") }
    var role by remember { mutableStateOf("Студент") }
    var subjectId by remember { mutableStateOf("1") }

    NavHost(navController = navController, startDestination = "entrance") {
        //entrance
        composable("entrance") { Entrance(navController) } // готово
        composable("authorization") { Authorization(navController) } // готово
        composable("registration") { Registration(navController) } // готово

        //account
        composable("account/{userId}/{role}") { Account(navController, userId, role) } // готово
        composable("editAccount/{userId}") { EditAccount(navController, userId) } // готово, только дату рождения надо каждый раз вводит самому, она не подставляеется из базы

        //settings
        composable("changingThePassword/{userId}/{role}") { ChangingThePassword(navController, userId, role) } // готово
        composable("settings/{userId}/{role}") { Settings(navController, userId, role) } // готово

        //student
        composable("certificate/{userId}") { Certificate(navController, userId) }
        composable("mainCertificate/{userId}") { MainCertificate(navController, userId) }
        composable("mainMyCourses/{userId}") { MainMyCourses(navController, userId) }
        composable("coursesPreview/{userId}") { CoursesPreview(navController, userId) }
        composable("mainSearchCourses/{userId}") { MainSearchCourses(navController, userId) }
        composable("lecture/{userId}") { Lecture(navController, userId) }
        composable("mainCourses/{userId}") { MainCourses(navController, userId) }
        composable("question/{userId}") { Question(navController, userId) }
        composable("completedCourses/{userId}") { CompletedCourses(navController, userId) }
        composable("deferredCourses/{userId}") { DeferredCourses(navController, userId) }
        composable("mainStudent/{userId}") { backStackEntry -> // готово
            userId = backStackEntry.arguments?.getString("userId")?: "1"
            MainStudent(navController, userId)
        }

        //support
        composable("support/{userId}") { Support(navController, userId) } // готово
        composable("appeal/{userId}/{subjectId}") { backStackEntry -> // готово
            userId = backStackEntry.arguments?.getString("userId")?: "1"
            subjectId = backStackEntry.arguments?.getString("subjectId")?: "1"
            Appeal(userId, subjectId)
        }
        composable("addAppeal/{userId}") { AddAppeal(navController, userId) } // готово
    }
}
