package eu.tutorials.myrecipeapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val recipeService = retrofit.create(ApiService::class.java)


interface ApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

//    @GET("filter.php")
//    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponse
//
//    @GET("lookup.php")
//    suspend fun getMealDetails(@Query("i") id: String): MealDetailsResponse
//
//    @GET("search.php")
//    suspend fun searchMeals(@Query("s") query: String): MealsResponse
}