package com.example.pm3elektrik.MotorListeSayfasi.FirebaseDatabase

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.google.firebase.database.FirebaseDatabase

class FireDatabase : AppCompatActivity(){

    val motor_liste = MotorModel()
    val ref = FirebaseDatabase.getInstance().reference

    fun MotorEkle(motorTag: String, motorGucKW: String, motorGucHP: String, motorDevir: String, motorNomTripAkimi: String,
                  motorInsaTipi: String, motorFlans: String, motorAdres: String, motorMCCYeri: String, motorDegTarihi: String){

        motor_liste.motorTag = motorTag
        motor_liste.motorGucKW = motorGucKW
        motor_liste.motorGucHP = motorGucHP
        motor_liste.motorDevir = motorDevir
        motor_liste.motorNomTripAkimi = motorNomTripAkimi
        motor_liste.motorInsaTipi = motorInsaTipi
        motor_liste.motorFlans = motorFlans
        motor_liste.motorAdres = motorAdres
        motor_liste.motorMCCYeri = motorMCCYeri
        motor_liste.motorDegTarihi = motorDegTarihi


        ref.child("MotorListe")
            .setValue(motor_liste).addOnCompleteListener {

                if(it.isSuccessful){
                    Toast.makeText(this,"Kayıt Yapıldı", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}