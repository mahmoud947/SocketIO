package com.example.retrofitwithnode.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitwithnode.databinding.MsgItemRowBinding
import com.example.retrofitwithnode.model.Msg
import javax.inject.Inject

class MsgAdapter @Inject constructor(): RecyclerView.Adapter<MsgAdapter.MyViewHolder>() {
    private var oldList: ArrayList<Msg> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(MsgItemRowBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      val  myList=oldList[position]
        holder.bind(myList)
    }

    override fun getItemCount(): Int =oldList.size

    inner class MyViewHolder(private val binding: MsgItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(msg: Msg) {
            binding.apply {
                tvItemMsg.text = msg.msg
            }
        }
    }

    fun setData(newList:ArrayList<Msg>){
        val diffUtils=MsgDiffUtils(oldList,newList)
        val diffResult=DiffUtil.calculateDiff(diffUtils)
        oldList.clear()
        oldList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

}