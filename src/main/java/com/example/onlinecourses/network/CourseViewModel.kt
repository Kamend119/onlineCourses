package com.example.onlinecourses.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CourseViewModel : ViewModel() {
    private val _courses = MutableStateFlow<List<CourseAllOtl>>(emptyList())
    val courses: StateFlow<List<CourseAllOtl>> = _courses

    fun getAllCourses() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getAllCourses()
                if (response.isSuccessful) {
                    _courses.value = response.body() ?: emptyList()
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}