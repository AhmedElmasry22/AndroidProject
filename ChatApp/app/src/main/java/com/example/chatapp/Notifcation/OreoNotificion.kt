package com.example.chatapp.Notifcation

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.icu.text.CaseMap
import android.icu.text.Normalizer.NO
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemServiceName


class OreoNotificion : AppCompatActivity() {
    private var notificion:NotificationManager?= null
    var context:Context?=null



    companion object{
        private const val ChanalId="com.example.chatapp"
        private const val Chanal_Name="Messege APP"
    }
    @TargetApi(Build.VERSION_CODES.O)

    private fun creatChanel(){
        val channel=NotificationChannel(
            ChanalId,
            Chanal_Name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.enableLights(false)
        channel.enableVibration(true)
        channel.lockscreenVisibility=Notification.VISIBILITY_PRIVATE

        getManger!!.createNotificationChannel(channel)
    }
    val getManger:NotificationManager? get(){
        if (notificion==null){
            val notificationManager =getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        }
        return notificion

    }
    @TargetApi(Build.VERSION_CODES.O)
    fun getOreoNotification(
            title: String?,
            body:String?,
            pendingIntent:PendingIntent?,
            soundUri:Uri?,
            icon:String?):Notification.Builder{
        return Notification.Builder(applicationContext, ChanalId)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(icon!!.toInt())
                .setSound(soundUri)
                .setAutoCancel(true)
    }







}