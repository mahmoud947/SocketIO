package com.example.retrofitwithnode.ui.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitwithnode.databinding.ActivityMainBinding
import com.example.retrofitwithnode.model.Msg
import com.example.retrofitwithnode.model.Post
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val viewModel:MainViewModel by viewModels()
    val adapters=MsgAdapter()
    var counter=0
    var id=0

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPush.visibility=View.INVISIBLE


        binding.rvMsg.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(this@MainActivity)
            adapter=adapters


        }

        viewModel.socketConnect()
        binding.etName.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnPush.visibility=View.INVISIBLE
                if (p0?.trim().isNullOrEmpty())
                    binding.rvMsg.smoothScrollToPosition(counter)

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
               binding.btnPush.visibility=View.VISIBLE
                if (p0?.trim().isNullOrEmpty()){
                    binding.btnPush.visibility=View.INVISIBLE
                }

            }

        })


        binding.btnPush.setOnClickListener{

            id++
            val msg=Msg(id,binding.etName.text.toString())
            viewModel.sendMessage(msg)
            binding.etName.text.clear()

        }
        viewModel.getMessage()
        viewModel.myMsgResponse.observe(this, Observer {msg->

            runOnUiThread(kotlinx.coroutines.Runnable {
                adapters.setData(msg)
                binding.rvMsg.smoothScrollToPosition(msg.size-1)
                Log.d("size",msg.size.toString())
                counter=msg.size-1
            })


        })



     binding.rvMsg.addOnLayoutChangeListener { view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
         if (bottom < oldBottom) {
             binding.rvMsg.scrollBy(0, oldBottom - bottom)
         }
     }


    }

}