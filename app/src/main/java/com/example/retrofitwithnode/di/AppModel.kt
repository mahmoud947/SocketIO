package com.example.retrofitwithnode.di

import com.example.retrofitwithnode.api.Remotely
import com.example.retrofitwithnode.utils.Constant.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModel {
    @Provides
    @Singleton
    fun provideRetrofit():Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApi():Remotely =
        provideRetrofit().create(Remotely::class.java)

    @Provides
    @Singleton
    fun provideSocketIo():Socket=
        IO.socket("http://192.168.1.3:5000/")



}