package com.example.onlinecourses.network

import com.google.gson.annotations.SerializedName

data class UserCredentials(
    val login: String,
    val password: String
)

data class User(
    val email: String,
    val login: String,
    val password: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("role_id") val roleId: Int,
    val gender: String,
    @SerializedName("data_birth") val dataBirth: String
)

data class Roles(
    @SerializedName("role_id") val roleId: Int,
    @SerializedName("name_role") val nameRole: String
)

data class CreateSupportRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("subject_id") val subjectId: Int,
    val message: String
)

data class SupportSubject(
    @SerializedName("subject_id") val subjectId: Int,
    val name: String
)

data class CreateAdminResponses(
    @SerializedName("request_id") val requestId: Int,
    @SerializedName("response_message") val responseMessage: String
)

data class GetUserRequest(
    @SerializedName("subject_id") val subjectId: Int,
    val message: String,
    val status: String
)

data class GetAllRequest(
    @SerializedName("id_request") val idRequest: Int,
    val fio: String,
    @SerializedName("subject_name") val subjectName: String,
    val message: String,
    val status: String
)

data class GetUser(
    val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)

data class CourseAllOtl(
    @SerializedName("course_id") val courseId: Int,
    @SerializedName("name_course") val nameCourse: String,
    @SerializedName("fio_owner") val fioOwner: String,
    @SerializedName("name_category") val nameCategory: String
)

data class CompletedProcessCourses(
    @SerializedName("course_id") val courseId: Int,
    @SerializedName("name_course") val nameCourse: String,
    @SerializedName("fio_owner") val fioOwner: String,
    @SerializedName("steps_completed") val stepsCompleted: Int,
    @SerializedName("name_category") val nameCategory: String
)

data class GetAllUserCerf(
    @SerializedName("course_id") val courseId: Int,
    @SerializedName("name_course") val nameCourse: String,
    @SerializedName("fio_owner") val fioOwner: String
)

data class GetUserCerf(
    @SerializedName("cerf_id") val cerfId: Int,
    @SerializedName("name_course") val nameCourse: String,
    @SerializedName("fio_owner") val fioOwner: String,
    @SerializedName("issue_date") val issueDate: String,
    @SerializedName("file_path") val filePath: String
)

data class Course(
    @SerializedName("name_course") val nameCourse: String,
    @SerializedName("fio_owner") val fioOwner: String,
    @SerializedName("name_category") val nameCategory: String,
    val description: String
)

data class StepInCourse(
    @SerializedName("step_id") val stepId: Int,
    @SerializedName("name_type") val nameType: String
)

data class StepsContent(
    @SerializedName("step_type") val stepType: String,
    @SerializedName("lecture_text") val lectureText: String?,
    @SerializedName("question_text") val questionText: String?,
    @SerializedName("answer_options") val answerOptions: List<String>?,
    @SerializedName("path_file") val pathFile: String?,
    @SerializedName("name_file") val nameFile: String?
)

data class DateStep(
    @SerializedName("date_completed") val dateCompleted: String,
    @SerializedName("steps_completed") val stepsCompleted: Int
)

data class OwnerCourses(
    @SerializedName("course_id") val courseId: Int,
    @SerializedName("name_course") val nameCourse: String,
    val description: String,
    @SerializedName("name_category") val nameCategory: String
)

data class AllUserCourses(
    @SerializedName("course_id") val courseId: Int,
    @SerializedName("course_name") val courseName: String,
    @SerializedName("fio_owner") val fioOwner: String,
    @SerializedName("name_category") val nameCategory: String,
    val status: String
)

data class ChangeStep(
    @SerializedName("step_id") val stepId: Int,
    @SerializedName("type_id") val typeId: Int,
    @SerializedName("lecture_text") val lectureText: String?,
    @SerializedName("question_text") val questionText: String?,
    @SerializedName("answer_option") val answerOption: String?,
    @SerializedName("correct_answers") val correctAnswers: List<String>?,
    @SerializedName("time_to_complete") val timeToComplete: String?,
    val path: String?,
    @SerializedName("name_file") val nameFile: String?
)

data class AnswerStep(
    @SerializedName("answer_id") val answerId: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("answer_text") val answerText: List<String>,
    val estimation: Int?,
    val comment: String
)

data class AdminStatistic(
    @SerializedName("total_users") val totalUsers: Int,
    @SerializedName("active_users") val activeUsers: Int
)

data class CourseStatistic(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("user_name") val userName: String,
    @SerializedName("total_steps") val totalSteps: Int,
    @SerializedName("completed_steps") val completedSteps: Int,
    @SerializedName("completion_percentage") val completionPercentage: Float,
    @SerializedName("is_active") val isActive: Boolean
)

data class NewUser(
    @SerializedName("user_name") val userName: String,
    @SerializedName("user_id") val userId: Int
)

data class LoginResponse(
    @SerializedName("user_id") val userId: Int
)

data class RegistrationResponse(
    @SerializedName("user_id") val userId: Int
)

data class MessageResponse(
    val message: String
)

data class AnswerResponse(
    @SerializedName("answer_id") val answerId: Int,
    @SerializedName("answer_step_id") val answerStepId: Int,
    @SerializedName("answer_files_id") val answerFilesId: Int
)

data class StepIdResponse(
    @SerializedName("step_id") val stepId: Int
)