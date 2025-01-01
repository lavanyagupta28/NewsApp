package com.example.newsapp.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    private val repository = NewsApi.NewsRepository()
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> get() = _articles
    fun fetchTopHeadlines(country: String, apiKey: String, category: String ?= null){
        viewModelScope.launch {
            try{
                val response = repository.getTopHeadlines(country, apiKey, category)
                _articles.value = response.articles
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }
}