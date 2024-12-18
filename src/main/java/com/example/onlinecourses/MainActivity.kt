package com.example.onlinecourses

import android.os.Bundle
import android.util.Log
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
import com.example.onlinecourses.administrator.MainAdministrator
import com.example.onlinecourses.administrator.coursesManagement.CoursesManagement
import com.example.onlinecourses.administrator.coursesManagement.LectureManagement
import com.example.onlinecourses.administrator.coursesManagement.MainCoursesManagement
import com.example.onlinecourses.administrator.support.AppealsAdministrator
import com.example.onlinecourses.administrator.support.SupportsAdministrator
import com.example.onlinecourses.administrator.userManagement.MainUserManagement
import com.example.onlinecourses.administrator.userManagement.UserManagement
import com.example.onlinecourses.courseOwner.MainOwner
import com.example.onlinecourses.courseOwner.MyCourses
import com.example.onlinecourses.courseOwner.Statistics
import com.example.onlinecourses.courseOwner.checkingProgress.AnswerUser
import com.example.onlinecourses.courseOwner.checkingProgress.MainChecking
import com.example.onlinecourses.courseOwner.checkingProgress.UserResponses
import com.example.onlinecourses.courseOwner.createCourse.CreateAnswer
import com.example.onlinecourses.courseOwner.createCourse.CreateLecture
import com.example.onlinecourses.courseOwner.createCourse.CreateStep
import com.example.onlinecourses.courseOwner.createCourse.MainCreate
import com.example.onlinecourses.courseOwner.editCourse.AnswerManagement
import com.example.onlinecourses.courseOwner.editCourse.EditAnswer
import com.example.onlinecourses.courseOwner.editCourse.EditLecture
import com.example.onlinecourses.courseOwner.editCourse.MainEdit
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

    NavHost(navController = navController, startDestination = "entrance") {
        //entrance
        composable("entrance") { Entrance(navController) } // готово
        composable("authorization") { Authorization(navController) } // готово
        composable("registration") { Registration(navController) } // готово

        //account
        composable("account") { Account(navController, userId, role) }
        composable("editAccount") { EditAccount(navController, userId, role) }

        //administrator
        composable("coursesManagement") { CoursesManagement(navController, userId) }
        composable("answerManagement") { AnswerManagement(navController, userId) }
        composable("lectureManagement") { LectureManagement(navController, userId) }
        composable("mainCoursesManagement") { MainCoursesManagement(navController, userId) }
        composable("appealsAdministrator") { AppealsAdministrator(navController, userId) }
        composable("supportsAdministrator") { SupportsAdministrator(navController, userId) }
        composable("mainUserManagement") { MainUserManagement(navController, userId) }
        composable("userManagement") { UserManagement(navController, userId) }
        composable("mainAdministrator/{userId}/{role}") { backStackEntry ->
            userId = backStackEntry.arguments?.getString("userId")?: "1"
            role = backStackEntry.arguments?.getString("role")?: "Студент"
            if (userId == "1" && role == "Студент") {
                Log.e("Navigation", "Аргументы переданы некорректно: userId=$userId, role=$role")
            }
            MainAdministrator(navController, userId, role)
        }

        //courseOwner
        composable("answerUser") { AnswerUser(navController, userId) }
        composable("mainChecking") { MainChecking(navController, userId) }
        composable("userResponses") { UserResponses(navController, userId) }
        composable("createAnswer") { CreateAnswer(navController, userId) }
        composable("createLecture") { CreateLecture(navController, userId) }
        composable("createStep") { CreateStep(navController, userId) }
        composable("mainCreate") { MainCreate(navController, userId) }
        composable("editAnswer") { EditAnswer(navController, userId) }
        composable("editLecture") { EditLecture(navController, userId) }
        composable("mainEdit") { MainEdit(navController, userId) }
        composable("mainOwner/{userId}/{role}") { backStackEntry ->
            userId = backStackEntry.arguments?.getString("userId")?: "1"
            role = backStackEntry.arguments?.getString("role")?: "Студент"
            if (userId == "1" && role == "Студент") {
                Log.e("Navigation", "Аргументы переданы некорректно: userId=$userId, role=$role")
            }
            MainOwner(navController, userId, role)
        }
        composable("myCourses") { MyCourses(navController, userId) }
        composable("statistics") { Statistics(navController, userId) }

        //settings
        composable("changingThePassword") { ChangingThePassword(navController, userId, role) }
        composable("settings") { Settings(navController, userId, role) }

        //student
        composable("certificate") { Certificate(navController, userId) }
        composable("mainCertificate") { MainCertificate(navController, userId) }
        composable("mainMyCourses") { MainMyCourses(navController, userId) }
        composable("coursesPreview") { CoursesPreview(navController, userId) }
        composable("mainSearchCourses") { MainSearchCourses(navController, userId) }
        composable("lecture") { Lecture(navController, userId) }
        composable("mainCourses") { MainCourses(navController, userId) }
        composable("question") { Question(navController, userId) }
        composable("completedCourses") { CompletedCourses(navController, userId) }
        composable("deferredCourses") { DeferredCourses(navController, userId) }
        composable("mainStudent/{userId}/{role}") { backStackEntry ->
            userId = backStackEntry.arguments?.getString("userId")?: "1"
            role = backStackEntry.arguments?.getString("role")?: "Студент"
            if (userId == "1" && role == "Студент") {
                Log.e("Navigation", "Аргументы переданы некорректно: userId=$userId, role=$role")
            }
            MainStudent(navController, userId, role)
        }

        //support
        composable("support") { Support(navController, userId) }
        composable("appeal") { Appeal(navController, userId) }
        composable("addAppeal") { AddAppeal(navController, userId) }

    }
}
