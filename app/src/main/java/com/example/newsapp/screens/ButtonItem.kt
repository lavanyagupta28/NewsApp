package com.example.newsapp.screens

data class ButtonItem(val lablel: String, var isEnabled: Boolean)

val buttonList = listOf(
    ButtonItem("Tech", false),
    ButtonItem("Sports", false),
    ButtonItem("Cinema", false)
)
