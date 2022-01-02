package com.example.retrofitwithnode.api

import com.example.retrofitwithnode.model.Post
import retrofit2.http.Body
import retrofit2.http.POST

interface Remotely {
    @POST("post")
    suspend fun addPost( @Body post:Post):Any
}