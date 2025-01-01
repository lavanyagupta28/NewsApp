package com.example.newsapp.screens

data class ButtonItem(val lablel: String, var isEnabled: Boolean, val category: String)

val buttonList = listOf(
    ButtonItem("Tech", false, "technology"),
    ButtonItem("Sports", false, "sports"),
    ButtonItem("Business", false, "business")
)
