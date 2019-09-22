package com.example.pm3elektrik.FirebaseCloudMessage.Servisler

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {

        val mesajBaslik = p0.notification?.title
        val mesajIcerik = p0.notification?.body
        val data = p0.data

    }
}