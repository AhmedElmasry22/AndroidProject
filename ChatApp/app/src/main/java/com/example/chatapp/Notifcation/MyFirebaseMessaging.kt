package com.example.chatapp.Notifcation

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import com.example.chatapp.ChatMessegeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessaging : FirebaseMessagingService(){
 override fun onMessageReceived(mRemote: RemoteMessage) {
  super.onMessageReceived(mRemote)
  val sented=mRemote.data["sent"]
  val user=mRemote.data["user"]
  val sheredPref=getSharedPreferences("PREFS", Context.MODE_PRIVATE)
  val currentOnLineUser=sheredPref.getString("current_user", "none")
  val firebaseUser:FirebaseUser=FirebaseAuth.getInstance().currentUser!!
  if(firebaseUser!=null&&sented==firebaseUser.uid){
   if(currentOnLineUser!=null){
    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
     sendOreiNotfiction(mRemote)
    }else{
     sendNotfiction(mRemote)

    }
   }
  }

 }
 fun sendNotfiction(mRemote: RemoteMessage) {

 }

 fun sendOreiNotfiction(mRemote: RemoteMessage) {
  val user=mRemote.data["user"]
  val title=mRemote.data["title"]
  val body=mRemote.data["body"]
  val icon=mRemote.data["icon"]

  val notification=mRemote.notification
  val j=user!!.replace("[\\D]".toRegex(),"").toInt()

  val intent=Intent(this,ChatMessegeActivity::class.java)
  val bundle=Bundle()
  bundle.putString("userId",user)
  intent.putExtras(bundle)
  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

  val pandeingIntent=PendingIntent.getActivity(this,j,intent,PendingIntent.FLAG_ONE_SHOT)
  val ring=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
  val oreointeion=OreoNotificion()
  val builder: Notification.Builder=oreointeion.getOreoNotification(title,body,pandeingIntent,ring,icon)





 }


}