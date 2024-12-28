package com.example.newsapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.newsapp.R
import java.time.LocalDate


data class User(
    val profileImage: Int,
    val username: String,
    val userFullName: String,
    val mail: String,
    val mobile: String,
    val dateOfBirth: LocalDate
)

@RequiresApi(Build.VERSION_CODES.O)
val user = User(
    profileImage = R.drawable.ic_profile,
    username = "@john_doe",
    userFullName = "John Doe",
    mail = "john.doe@example.com",
    mobile = "+1234567890",
    dateOfBirth = LocalDate.of(1990, 5, 15)
)