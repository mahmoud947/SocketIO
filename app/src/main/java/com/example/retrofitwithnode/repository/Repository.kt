package com.example.retrofitwithnode.repository

import com.example.retrofitwithnode.api.Remotely
import com.example.retrofitwithnode.model.Post
import io.socket.client.Socket
import javax.inject.Inject

class Repository @Inject constructor(
    private val remotely: Remotely,
    private val socket: Socket
) {
    suspend fun addNewPost(post: Post): Any=
        remotely.addPost(post)
    suspend fun socketConnect(): Socket = socket
}