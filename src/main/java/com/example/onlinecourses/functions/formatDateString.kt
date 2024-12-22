package com.example.onlinecourses.functions

import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun formatDateString(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val date = LocalDate.parse(dateString, formatter)
    return date.format(DateTimeFormatter.ISO_DATE)
}