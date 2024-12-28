package com.example.newsapp.screens

import kotlinx.serialization.Serializable


@Serializable
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
