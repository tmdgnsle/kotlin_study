package com.example.youtube_clone

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.*

class YoutubeItem(
    val id: Int, val title: String, val content: String, val video: String, val thumbnail: String
)

interface RetrofitService {
    @GET("youtube/list/")
    fun getYoutubeItemList(): Call<ArrayList<YoutubeItem>>
}