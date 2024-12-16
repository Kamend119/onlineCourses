package com.example.onlinecourses.network

import retrofit2.http.*
import retrofit2.Response

interface ApiService {
    @GET("login")
    suspend fun login(@Body credentials: UserCredentials): Response<LoginResponse>

    @POST("registration")
    suspend fun registration(@Body user: User): Response<RegistrationResponse>

    @GET("get_roles")
    suspend fun getRoles(): Response<List<Roles>>

    @POST("create_support_request")
    suspend fun createSupportRequest(@Body request: CreateSupportRequest): Response<MessageResponse>

    @GET("get_support_subject")
    suspend fun getSupportSubject(): Response<List<SupportSubject>>

    @POST("create_admin_responses")
    suspend fun createAdminResponses(@Body response: CreateAdminResponses): Response<MessageResponse>

    @GET("get_user_request")
    suspend fun getUserRequest(@Query("user_id") userId: Int): Response<List<GetUserRequest>>

    @GET("get_all_request")
    suspend fun getAllRequest(): Response<List<GetAllRequest>>

    @GET("get_user_data")
    suspend fun getUserData(@Query("user_id") userId: Int): Response<GetUser>

    @POST("update_user_data")
    suspend fun updateUserData(
        @Query("user_id") userId: Int,
        @Query("email") email: String,
        @Query("first_name") firstName: String,
        @Query("last_name") lastName: String,
        @Query("date_birth") dateBirth: String
    ): Response<MessageResponse>

    @POST("change_user_password")
    suspend fun changeUserPassword(
        @Query("user_id") userId: Int,
        @Query("password") password: String
    ): Response<MessageResponse>

    @GET("get_courses_otl")
    suspend fun getCoursesOtl(@Query("user_id") userId: Int): Response<List<CourseAllOtl>>

    @GET("get_completed_courses")
    suspend fun getCompletedCourses(@Query("user_id") userId: Int): Response<List<CompletedProcessCourses>>

    @GET("get_all_certificates_user")
    suspend fun getAllCertificatesUser(@Query("user_id") userId: Int): Response<List<GetAllUserCerf>>

    @GET("get_certificates_user_course")
    suspend fun getCertificatesUserCourse(
        @Query("user_id") userId: Int,
        @Query("course_id") courseId: Int
    ): Response<List<GetUserCerf>>

    @GET("get_all_courses")
    suspend fun getAllCourses(): Response<List<CourseAllOtl>>

    @GET("get_courses_in_process")
    suspend fun getCoursesInProcess(@Query("user_id") userId: Int): Response<List<CompletedProcessCourses>>

    @GET("get_one_cours")
    suspend fun getOneCourse(@Query("course_id") courseId: Int): Response<Course>

    @POST("create_courses_students")
    suspend fun createCoursesStudents(
        @Query("user_id") userId: Int,
        @Query("course_id") courseId: Int
    ): Response<MessageResponse>

    @GET("get_steps_in_courses")
    suspend fun getStepsInCourses(@Query("course_id") courseId: Int): Response<List<StepInCourse>>

    @GET("get_step_content")
    suspend fun getStepContent(@Query("step_id") stepId: Int): Response<List<StepsContent>>

    @POST("create_answer")
    suspend fun createAnswer(
        @Query("answer_user") answerUser: String,
        @Query("step_id") stepId: Int,
        @Query("user_id") userId: Int,
        @Query("comment") comment: String,
        @Query("path") path: String,
        @Query("name_file") nameFile: String
    ): Response<AnswerResponse>

    @POST("change_answer")
    suspend fun changeAnswer(
        @Query("answer_id") answerId: Int,
        @Query("answer_user") answerUser: String,
        @Query("comment") comment: String,
        @Query("path") path: String,
        @Query("name_file") nameFile: String
    ): Response<MessageResponse>

    @GET("get_steps_per_day")
    suspend fun getStepsPerDay(@Query("user_id") userId: Int): Response<List<DateStep>>

    @GET("get_all_owner_courses")
    suspend fun getAllOwnerCourses(@Query("user_id") userId: Int): Response<List<OwnerCourses>>

    @GET("get_all_user_course")
    suspend fun getAllUserCourse(@Query("user_id") userId: Int): Response<List<AllUserCourses>>

    @POST("change_step")
    suspend fun changeStep(@Body changeStep: ChangeStep): Response<MessageResponse>

    @POST("create_lecture_step")
    suspend fun createLectureStep(
        @Query("lesson_id") lessonId: Int,
        @Query("lecture_text") lectureText: String
    ): Response<StepIdResponse>

    @POST("create_open_question_step")
    suspend fun createOpenQuestionStep(
        @Query("lesson_id") lessonId: Int,
        @Query("question_text") questionText: String
    ): Response<StepIdResponse>

    @POST("create_close_question_step")
    suspend fun createCloseQuestionStep(
        @Query("lesson_id") lessonId: Int,
        @Query("question_text") questionText: String,
        @Query("answer_options") answerOptions: List<String>,
        @Query("correct_answer") correctAnswer: List<String>
    ): Response<StepIdResponse>

    @POST("create_question_with_file_step")
    suspend fun createQuestionWithFileStep(
        @Query("lesson_id") lessonId: Int,
        @Query("question_text") questionText: String,
        @Query("path") path: String,
        @Query("name_file") nameFile: String
    ): Response<StepIdResponse>

    @GET("get_answers_by_step")
    suspend fun getAnswersByStep(@Query("step_id") stepId: Int): Response<List<AnswerStep>>

    @POST("set_estimation")
    suspend fun setEstimation(
        @Query("answer_id") answerId: Int,
        @Query("estimation_value") estimationValue: Int
    ): Response<MessageResponse>

    @POST("add_comment_to_step")
    suspend fun addCommentToStep(
        @Query("answer_id") answerId: Int,
        @Query("comment") comment: String
    ): Response<MessageResponse>

    @GET("get_admin_statistics")
    suspend fun getAdminStatistics(): Response<AdminStatistic>

    @GET("get_course_statistics")
    suspend fun getCourseStatistics(@Query("course_id") courseId: Int): Response<List<CourseStatistic>>

    @GET("get_new_users_for_period")
    suspend fun getNewUsersForPeriod(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Response<List<NewUser>>

    @GET("get_all_users")
    suspend fun getAllUsers(): Response<List<User>>

    @POST("delete_user_by_id")
    suspend fun deleteUserById(@Query("user_id") userId: Int): Response<MessageResponse>

    @POST("delete_step_by_id")
    suspend fun deleteStepById(@Query("step_id") stepId: Int): Response<MessageResponse>

    @POST("delete_course_by_id")
    suspend fun deleteCourseById(@Query("course_id") courseId: Int): Response<MessageResponse>
}