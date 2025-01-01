package com.example.newsapp.screens
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = null,
        @Query("apiKey") apiKey: String,
        @Query("category") category: String? = null,
        @Query("sources") sources: String? = null,
        @Query("q") query: String? = null,
        @Query("pageSize") pageSize: Int? = 20,
        @Query("page") page: Int? = 1
    ): NewsResponse

    object RetrofitInstance{
        private const val BASE_URL = "https://newsapi.org/"

        val api: NewsApi by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsApi::class.java)
        }

    }


    class NewsRepository {
        private val api = RetrofitInstance.api

        suspend fun getTopHeadlines(
            country: String,
            apiKey: String,
            category: String? = null
        ): NewsResponse {
            Log.d("NewsRepository", "Fetching top headlines - country: $country, category: $category")
            return api.getTopHeadlines(
                country = country,
                apiKey = apiKey,
                category = category
            )
        }
    }

}
