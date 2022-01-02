package com.example.retrofitwithnode.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.example.retrofitwithnode.model.Msg

class MsgDiffUtils(
    private val oldList: ArrayList<Msg>,
    private val newList: ArrayList<Msg>,
):DiffUtil.Callback() {
    override fun getOldListSize(): Int =oldList.size

    override fun getNewListSize(): Int =newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean=
        oldList[oldItemPosition].id==newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return when{
           oldList[oldItemPosition].msg!=newList[newItemPosition].msg ->{
               false
           }
           oldList[oldItemPosition].id!=newList[newItemPosition].id ->{
               false
           }
           else -> false
       }
    }


}