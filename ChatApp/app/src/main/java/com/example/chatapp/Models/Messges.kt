package com.example.chatapp.Models

data class Messges(val sender:String="",val received:String="",val messege:String="",
                   val isSeen:Boolean=false,val Uri:String="",val messegeUid:String="")