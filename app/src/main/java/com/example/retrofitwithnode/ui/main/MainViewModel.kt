package com.example.retrofitwithnode.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitwithnode.model.Msg
import com.example.retrofitwithnode.model.Post
import com.example.retrofitwithnode.repository.Repository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Emitter
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repository) : ViewModel() {
    var myResponse: MutableLiveData<Any> = MutableLiveData()
    var myMsgResponse: MutableLiveData<ArrayList<Msg>> = MutableLiveData()
    private val msgList = ArrayList<Msg>()

    fun addNewPost(post: Post) {
        viewModelScope.launch {
            val response = repo.addNewPost(post)
            myResponse.value = response
        }
    }
     fun socketConnect(){
         viewModelScope.launch {
             repo.socketConnect().connect()
         }
     }
    fun sendMessage(msg:Msg){
        val jsonString = Gson().toJson(msg)
        val gson=JSONObject(jsonString)
        viewModelScope.launch {
            repo.socketConnect().emit("send-message",gson)
        }
    }
    fun getMessage(){
        viewModelScope.launch {
            val onNewMessage:io.socket.emitter.Emitter.Listener = io.socket.emitter.Emitter.Listener() {
                viewModelScope.launch {
                    val json=it[0]
                    val gson=Gson()
                  val msges= gson.fromJson(json.toString(),Msg::class.java)
                    addIssuePost(msges)
                }

            }

            repo.socketConnect().on("send-message",onNewMessage)
        }
    }

    fun addIssuePost(msg: Msg) {
        msgList.add(msg)
        myMsgResponse.value = msgList
    }

}