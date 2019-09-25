package com.example.pm3elektrik.FirebaseCloudMessage.Servisler


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.pm3elektrik.AnaSayfa.AnaSayfa
import com.example.pm3elektrik.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    val NOTIF_CHANNEL_ID = "my_channel_01"

    override fun onMessageReceived(p0: RemoteMessage) {

        val baslik = p0.data.get("baslik")
        val icerik = p0.data.get("icerik")
        val bildirim = p0.data.get("bildirim_turu")

        bildirimGonder(baslik, icerik, bildirim)
    }

    private fun bildirimGonder(baslik: String?, icerik: String?, bildirim: String?) {

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(NOTIF_CHANNEL_ID) == null) {
                createNotifChannel(this) }
        }

        val pendingIntent = Intent(this, AnaSayfa::class.java)
        pendingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        pendingIntent.putExtra("motor_liste_sayfa", bildirim)

        val bildirimPendingIntent = PendingIntent.getActivity(this, 10, pendingIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this,NOTIF_CHANNEL_ID)
            .setSmallIcon(R.drawable.motor_passive)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.motor_passive))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentTitle(baslik)
            .setContentText(icerik)
            //.setSubText(dene)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(icerik))
            .setNumber(987)
            .setOnlyAlertOnce(true)
            .setContentIntent(bildirimPendingIntent)

        notificationManager.notify(1000, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotifChannel(mContext: Context) {

        val channel = NotificationChannel(NOTIF_CHANNEL_ID,"MyApp Events",NotificationManager.IMPORTANCE_LOW)

        channel.description = "MyApp Events Controls"
        channel.setShowBadge(false)
        //channel.lockscreenVisibility(Notification.VISIBILITY_PUBLIC)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}