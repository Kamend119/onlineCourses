package com.example.onlinecourses.network

import RetrofitClient
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinecourses.functions.formatDateString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

data class DailyStatsState(
    val isLoading: Boolean = false,
    val data: List<DateStep> = emptyList(),
    val error: String? = null
)
data class UserDataState(
    val isLoading: Boolean = false,
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val date_birth: String = "",
    val error: String? = null
)
data class LectureUiState(
    val isLoading: Boolean = false,
    val lectureText: String? = null,
    val errorMessage: String? = null
)

class RegistrationViewModel() : ViewModel() {
    var roles by mutableStateOf<List<Roles>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var registrationResult by mutableStateOf<String?>(null)
        private set

    // Загружаем список ролей
    fun loadRoles() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitClient.instance.getRoles()
                if (response.isSuccessful) {
                    roles = response.body() ?: emptyList()
                    Log.d("RegistrationViewModel", "Роли загружены: ${roles.size} ролей")
                } else {
                    registrationResult = "Ошибка загрузки ролей: ${response.message()}"
                    Log.e("RegistrationViewModel", "Ошибка загрузки ролей: ${response.message()}")
                }
            } catch (e: Exception) {
                registrationResult = "Ошибка подключения: ${e.message}"
                Log.e("RegistrationViewModel", "Ошибка подключения: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }


    // Регистрируем пользователя
    fun registerUser(user: User, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                Log.d("RegistrationViewModel", "Отправка данных на сервер: $user")
                val response = RetrofitClient.instance.registration(user)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.userId != null) {  // Проверяем наличие userId
                        registrationResult = "Регистрация успешна. Ваш ID: ${body.userId}"
                        onSuccess()
                    } else {
                        registrationResult = "Ошибка регистрации: userId отсутствует"
                    }
                } else {
                    registrationResult = "Ошибка регистрации: ${response.message()}"
                    Log.e("RegistrationViewModel", "Ошибка регистрации: ${response.message()}")
                }
            } catch (e: Exception) {
                registrationResult = "Ошибка подключения: ${e.message}"
                Log.e("RegistrationViewModel", "Ошибка подключения: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}

class ChangePasswordViewModel : ViewModel() {
    var errorMessage = ""
    var isLoading = false
    var successMessage = ""

    private val apiService = RetrofitClient.instance

    var onPasswordChanged: (() -> Unit)? = null

    fun changePassword(userId: Int, newPassword: String, repeatPassword: String) {
        if (newPassword != repeatPassword) {
            errorMessage = "Пароли не совпадают"
            return
        }
        if (newPassword.isBlank()) {
            errorMessage = "Заполните новый пароль"
            return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                val response = apiService.changeUserPassword(userId, newPassword)
                handleResponse(response)
            } catch (e: Exception) {
                errorMessage = "Не удалось изменить пароль. Попробуйте еще раз."
                isLoading = false
                Log.e("ChangePasswordViewModel", "Error: ${e.localizedMessage}")
            }
        }
    }

    private fun handleResponse(response: Response<MessageResponse>) {
        isLoading = false
        if (response.isSuccessful) {
            successMessage = "Пароль успешно изменен"
            errorMessage = ""

            onPasswordChanged?.invoke()
        } else {
            errorMessage = "Не удалось изменить пароль. Попробуйте еще раз."
        }
    }
}

class AuthorizationViewModel : ViewModel() {
    private val apiService: ApiService = RetrofitClient.instance
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    fun login(login: String, password: String, onLoginSuccess: (String, String) -> Unit) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Передаем login и password как параметры GET-запроса
                val response = apiService.login(login, password)
                handleResponse(response, onLoginSuccess)
            } catch (e: Exception) {
                _isLoading.value = false
                // Обработка ошибки (например, показать уведомление)
            }
        }
    }

    private fun handleResponse(response: Response<LoginResponse>, onLoginSuccess: (String, String) -> Unit) {
        _isLoading.value = false
        if (response.isSuccessful) {
            val loginResponse = response.body()
            if (loginResponse != null) {
                onLoginSuccess(loginResponse.userId.toString(), loginResponse.roleName)
            }
        } else {
            // Обработка ошибки на сервере
        }
    }
}

class SupportViewModel : ViewModel() {
    var supportRequests = mutableStateOf<List<GetUserRequest>>(emptyList())
    var isLoadingRequests = mutableStateOf(true)
    var requestError = mutableStateOf<String?>(null)

    fun getSupportRequests(userId: String) {
        viewModelScope.launch {
            try {
                isLoadingRequests.value = true
                requestError.value = null
                val response = RetrofitClient.instance.getUserRequest(userId.toInt())
                if (response.isSuccessful) {
                    response.body()?.let {
                        supportRequests.value = it
                    } ?: run {
                        requestError.value = "Нет данных"
                    }
                } else {
                    requestError.value = "Ошибка: ${response.message()}"
                }
            } catch (e: Exception) {
                requestError.value = "Ошибка сети: ${e.message}"
            } finally {
                isLoadingRequests.value = false
            }
        }
    }

    var supportSubjects = mutableStateOf<List<SupportSubject>>(emptyList())
    var isLoadingSubjects = mutableStateOf(false)
    var subjectError = mutableStateOf<String?>(null)

    fun loadSupportSubjects() {
        viewModelScope.launch {
            isLoadingSubjects.value = true
            subjectError.value = null
            try {
                val response = RetrofitClient.instance.getSupportSubject()
                if (response.isSuccessful) {
                    supportSubjects.value = response.body() ?: emptyList()
                } else {
                    subjectError.value = "Ошибка загрузки тем обращения: ${response.message()}"
                }
            } catch (e: Exception) {
                subjectError.value = "Ошибка подключения: ${e.message}"
            } finally {
                isLoadingSubjects.value = false
            }
        }
    }

    fun createSupportRequest(userId: Int, subjectId: Int, message: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoadingSubjects.value = true
            try {
                val request = CreateSupportRequest(userId, subjectId, message)
                val response = RetrofitClient.instance.createSupportRequest(request)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    subjectError.value = "Ошибка создания обращения: ${response.message()}"
                }
            } catch (e: Exception) {
                subjectError.value = "Ошибка подключения: ${e.message}"
            } finally {
                isLoadingSubjects.value = false
            }
        }
    }
}

class MainStudentViewModel() : ViewModel() {

    private val _dailyStatsState = MutableStateFlow(DailyStatsState())
    val dailyStatsState: StateFlow<DailyStatsState> = _dailyStatsState

    fun fetchDailyStats(userId: Int) {
        viewModelScope.launch {
            _dailyStatsState.value = DailyStatsState(isLoading = true)
            try {
                val response = RetrofitClient.instance.getStepsPerDay(userId)
                if (response.isSuccessful) {
                    val stats = response.body() ?: emptyList()
                    _dailyStatsState.value = DailyStatsState(isLoading = false, data = stats)
                } else {
                    _dailyStatsState.value = DailyStatsState(
                        isLoading = false,
                        error = "Ошибка сервера: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _dailyStatsState.value = DailyStatsState(
                    isLoading = false,
                    error = "Ошибка: ${e.localizedMessage}"
                )
            }
        }
    }

}

class EditAccountViewModel : ViewModel() {
    private val _userData = MutableStateFlow(UserDataState())
    val userData: StateFlow<UserDataState> = _userData

    var updateResult by mutableStateOf<MessageResponse?>(null)
    var errorMessage by mutableStateOf<String?>(null)

    fun fetchUserData(userId: Int) {
        viewModelScope.launch {
            _userData.value = _userData.value.copy(isLoading = true)
            try {
                val response: Response<GetUser> = RetrofitClient.instance.getUserData(userId)
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    _userData.value = UserDataState(
                        isLoading = false,
                        email = user.email,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        date_birth = user.date_birth
                    )
                } else {
                    _userData.value = _userData.value.copy(
                        isLoading = false,
                        error = "Не удалось загрузить данные пользователя"
                    )
                }
            } catch (e: Exception) {
                _userData.value = _userData.value.copy(
                    isLoading = false,
                    error = e.message ?: "Произошла ошибка"
                )
            }
        }
    }

    fun updateFirstName(firstName: String) {
        _userData.value = _userData.value.copy(firstName = firstName)
    }

    fun updateLastName(newValue: String) {
        _userData.value = _userData.value.copy(lastName = newValue)
    }

    fun updateEmail(newValue: String) {
        _userData.value = _userData.value.copy(email = newValue)
    }

    fun updateDateBirth(newValue: String) {
        _userData.value = _userData.value.copy(date_birth = newValue)
    }

    fun updateUserData(
        userId: Int,
        email: String,
        firstName: String,
        lastName: String,
        dateBirth: String
    ) {
        viewModelScope.launch {
            val formattedDate = formatDateString(dateBirth)
            try {
                val response: Response<MessageResponse> = RetrofitClient.instance.updateUserData(
                    userId = userId,
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    dateBirth = formattedDate
                )
                if (response.isSuccessful) {
                    updateResult = response.body()
                } else {
                    errorMessage = response.message()
                }
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            }
        }
    }
}

class AccountViewModel() : ViewModel() {
    private val _userData = MutableStateFlow(UserDataState())
    val userData: StateFlow<UserDataState> = _userData

    fun fetchUserData(userId: Int) {
        viewModelScope.launch {
            _userData.value = _userData.value.copy(isLoading = true)
            try {
                val response: Response<GetUser> = RetrofitClient.instance.getUserData(userId)
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    _userData.value = UserDataState(
                        isLoading = false,
                        email = user.email,
                        firstName = user.firstName,
                        lastName = user.lastName
                    )
                } else {
                    _userData.value = _userData.value.copy(
                        isLoading = false,
                        error = "Не удалось загрузить данные пользователя"
                    )
                }
            } catch (e: Exception) {
                _userData.value = _userData.value.copy(
                    isLoading = false,
                    error = e.message ?: "Произошла ошибка"
                )
            }
        }
    }
}

class CourseViewModel : ViewModel() {
    var categories = mutableStateOf<List<Categories>>(emptyList())
    var courses = mutableStateOf<List<CourseAllOtl>>(emptyList())
    var filteredCourses = mutableStateOf<List<CourseAllOtl>>(emptyList())
    var isLoadingCourses = mutableStateOf(true)
    var courseError = mutableStateOf<String?>(null)

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getCategories()
                if (response.isSuccessful) {
                    categories.value = response.body() ?: emptyList()
                } else {
                    courseError.value = "Ошибка загрузки категорий: ${response.message()}"
                }
            } catch (e: Exception) {
                courseError.value = "Ошибка сети: ${e.message}"
            }
        }
    }

    fun fetchCourses() {
        viewModelScope.launch {
            isLoadingCourses.value = true
            courseError.value = null
            try {
                val response = RetrofitClient.instance.getAllCourses()
                if (response.isSuccessful) {
                    courses.value = response.body() ?: emptyList()
                    filteredCourses.value = courses.value
                } else {
                    courseError.value = "Ошибка загрузки курсов: ${response.message()}"
                }
            } catch (e: Exception) {
                courseError.value = "Ошибка сети: ${e.message}"
            } finally {
                isLoadingCourses.value = false
            }
        }
    }
}

class MyCoursesViewModel : ViewModel() {
    var categories = mutableStateOf<List<Categories>>(emptyList())
    var courses = mutableStateOf<List<CompletedProcessCourses>>(emptyList())
    var filteredCourses = mutableStateOf<List<CompletedProcessCourses>>(emptyList())
    var isLoadingCourses = mutableStateOf(true)
    var courseError = mutableStateOf<String?>(null)

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getCategories()
                if (response.isSuccessful) {
                    categories.value = response.body() ?: emptyList()
                } else {
                    courseError.value = "Ошибка загрузки категорий: ${response.message()}"
                }
            } catch (e: Exception) {
                courseError.value = "Ошибка сети: ${e.message}"
            }
        }
    }

    fun fetchCourses(userId: Int) {
        viewModelScope.launch {
            isLoadingCourses.value = true
            courseError.value = null
            try {
                val response = RetrofitClient.instance.getCoursesInProcess(userId)
                if (response.isSuccessful) {
                    courses.value = response.body() ?: emptyList()
                    filteredCourses.value = courses.value
                } else {
                    courseError.value = "Ошибка загрузки курсов: ${response.message()}"
                }
            } catch (e: Exception) {
                courseError.value = "Ошибка сети: ${e.message}"
            } finally {
                isLoadingCourses.value = false
            }
        }
    }
}

class CourseViewModelCreateStudentCourse() : ViewModel() {
    var courseName = ""
    var courseDescription = ""
    val isLoading = mutableStateOf(false) // Используем MutableState для Compose
    var message = ""

    fun getCourseDetails(courseId: Int) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response: Response<Course> = RetrofitClient.instance.getOneCourse(courseId)
                if (response.isSuccessful) {
                    val course = response.body()
                    course?.let {
                        courseName = it.nameCourse
                        courseDescription = it.description
                    }
                } else {
                    message = "Ошибка загрузки курса"
                }
            } catch (e: Exception) {
                message = "Ошибка сети: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun enrollInCourse(userId: Int, courseId: Int) {
        viewModelScope.launch {
            try {
                val response: Response<MessageResponse> = RetrofitClient.instance.createCoursesStudents(userId, courseId)
                if (response.isSuccessful) {
                    message = response.body()?.message ?: "Update!!!"
                } else {
                    message = "Ошибка при поступлении на курс"
                }
            } catch (e: Exception) {
                message = "Ошибка сети: ${e.message}"
            }
        }
    }
}

class CourseViewModelMainCourses : ViewModel() {
    var course: Course? by mutableStateOf(null)
        private set

    val stepsList = mutableStateListOf<StepInCourse>()

    fun loadCourse(courseId: Int, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getOneCourse(courseId)
                if (response.isSuccessful) {
                    course = response.body()
                } else {
                    onError("Ошибка загрузки курса: ${response.message()} (${response.code()})")
                }
            } catch (e: Exception) {
                onError("Не удалось загрузить данные курса: ${e.localizedMessage}")
            }
        }
    }

    fun loadSteps(courseId: Int, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getStepsInCourses(courseId)
                if (response.isSuccessful) {
                    stepsList.clear()
                    stepsList.addAll(response.body() ?: emptyList())
                } else {
                    onError("Ошибка загрузки шагов: ${response.message()} (${response.code()})")
                }
            } catch (e: Exception) {
                onError("Не удалось загрузить шаги курса: ${e.localizedMessage}")
            }
        }
    }
}

class LectureViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(LectureUiState())
    val uiState: StateFlow<LectureUiState> = _uiState

    fun loadLecture(stepId: Int) {
        _uiState.value = LectureUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getStepContent(stepId)
                if (response.isSuccessful) {
                    val steps = response.body()
                    val lectureStep = steps?.find { it.stepType == "Лекция" }
                    _uiState.value = LectureUiState(
                        isLoading = false,
                        lectureText = lectureStep?.lectureText
                    )
                } else {
                    _uiState.value = LectureUiState(
                        isLoading = false,
                        errorMessage = "Error: ${response.code()} ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = LectureUiState(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
}

class QuestionViewModel : ViewModel() {
    private val _stepData = MutableStateFlow<StepsContent?>(null)
    val stepData: StateFlow<StepsContent?> = _stepData

    private val _userAnswer = MutableStateFlow<GetAnswersUser?>(null)
    val userAnswer: StateFlow<GetAnswersUser?> = _userAnswer

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadStepData(stepId: Int, userId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val stepResponse = RetrofitClient.instance.getStepContent(stepId)
                val answerResponse = RetrofitClient.instance.getAnswersByStepForUser(stepId, userId)

                if (stepResponse.isSuccessful) {
                    _stepData.value = stepResponse.body()?.firstOrNull()
                } else {
                    _errorMessage.value = "Ошибка загрузки данных шага"
                }

                if (answerResponse.isSuccessful) {
                    _userAnswer.value = answerResponse.body()
                } else {
                    _userAnswer.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun submitAnswer(
        userId: Int,
        stepId: Int,
        answerText: String,
        fileUri: Uri?,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val response = if (_userAnswer.value == null) {
                    RetrofitClient.instance.createAnswer(
                        answerUser = answerText,
                        stepId = stepId,
                        userId = userId,
                        comment = "",
                        path = fileUri?.path ?: "",
                        nameFile = fileUri?.lastPathSegment ?: ""
                    )
                } else {
                    RetrofitClient.instance.changeAnswer(
                        answerId = _userAnswer.value!!.answer_id,
                        answerUser = answerText,
                        comment = "",
                        path = fileUri?.path ?: "",
                        nameFile = fileUri?.lastPathSegment ?: ""
                    )
                }

                if (response.isSuccessful) {
                    Toast.makeText(context, "Ответ сохранен", Toast.LENGTH_SHORT).show()
                    loadStepData(stepId, userId)
                } else {
                    Toast.makeText(context, "Ошибка сохранения", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}