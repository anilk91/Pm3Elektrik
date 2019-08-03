package com.example.pm3elektrik.FireBaseKayitSinifi

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class FirebaseKayit : Application(){

    override fun onCreate() {
        super.onCreate()

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

    }
}