package com.example.pm3elektrik.DigerBilgilerSayfasi.DriveUniteleriSayfasi.DriveUniteEkle


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pm3elektrik.DigerBilgilerSayfasi.DriveUniteleriSayfasi.DriveUniteModel.DriveModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel

import com.example.pm3elektrik.R
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class DriveUniteEkle : Fragment() {


    val driveMotorListe = DriveModel()
    val motorListe = MotorModel()
    val driveUniteRef = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Drive")
    val motorRef = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Motor")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drive_unite_ekle, container, false)

        val ekle = view.findViewById<Button>(R.id.buttonDriveEkle)

        ekle.setOnClickListener {

            val tag = view.findViewById<EditText>(R.id.etDriveMotorTag).text.toString().toUpperCase()
            val guc = view.findViewById<EditText>(R.id.etDriveGuc).text.toString()
            val akim = view.findViewById<EditText>(R.id.etDriveAkim).text.toString()
            val voltaj = view.findViewById<EditText>(R.id.etDriveVoltaj).text.toString()
            val modul = view.findViewById<EditText>(R.id.etDriveModul).text.toString().toUpperCase()
            val code = view.findViewById<EditText>(R.id.etDriveCode).text.toString().toUpperCase()
            val seriNo = view.findViewById<EditText>(R.id.etDriveSeriNo).text.toString()
            val igbt = view.findViewById<EditText>(R.id.etDriveIGBT).text.toString().toUpperCase()
            val kondansator = view.findViewById<EditText>(R.id.etDriveKondansator).text.toString().toUpperCase()
            val thickFirm = view.findViewById<EditText>(R.id.etDriveThick).text.toString().toUpperCase()
            val anaTetikleme = view.findViewById<EditText>(R.id.etDriveTetiklemeAna).text.toString().toUpperCase()
            val igbtTetikleme = view.findViewById<EditText>(R.id.etDriveIGBTTetikleme).text.toString().toUpperCase()
            val tetiklemePCB = view.findViewById<EditText>(R.id.etDriveTetiklemeBesPCB).text.toString().toUpperCase()
            val nintBes = view.findViewById<EditText>(R.id.etDriveNintBesleme).text.toString().toUpperCase()
            val bes24v = view.findViewById<EditText>(R.id.etDrive24VBesleme).text.toString().toUpperCase()
            val kontUnit = view.findViewById<EditText>(R.id.etDriveKontrolUnit).text.toString().toUpperCase()
            val fan = view.findViewById<EditText>(R.id.etDriveFan).text.toString().toUpperCase()
            val sigorta1 = view.findViewById<EditText>(R.id.etDriveSigorta1).text.toString().toUpperCase()
            val sigorta2 = view.findViewById<EditText>(R.id.etDriveSigorta2).text.toString().toUpperCase()
            val uniteDegTarih = view.findViewById<EditText>(R.id.etDriveUniteDegTarih).text.toString()
            val motorDegTarihi = view.findViewById<EditText>(R.id.etDriveMotorDegTarihi).text.toString()

            firebaseDBEkle(tag,guc,akim,voltaj,modul,code,seriNo,igbt,kondansator,thickFirm,anaTetikleme,igbtTetikleme,tetiklemePCB,nintBes,bes24v,kontUnit,
                fan,sigorta1,sigorta2,uniteDegTarih,motorDegTarihi)
        }



        return view
    }

    private fun firebaseDBEkle(tag: String, guc: String, akim: String, voltaj: String, modul: String, code: String, seriNo: String, igbt: String, kondansator: String, thickFirm: String, anaTetikleme: String, igbtTetikleme: String,
                               tetiklemePCB: String, nintBes: String, bes24v: String, kontUnit: String, fan: String, sigorta1: String, sigorta2: String, uniteDegTarih: String, motorDegTarihi: String) {

        driveMotorListe.driveTagNo = tag
        driveMotorListe.driveGuc = guc
        driveMotorListe.driveAkim = akim
        driveMotorListe.driveVoltaj = voltaj
        driveMotorListe.driveModul = modul
        driveMotorListe.driveCode = code
        driveMotorListe.driveSeriNo = seriNo
        driveMotorListe.driveIGBT = igbt
        driveMotorListe.driveKondansator = kondansator
        driveMotorListe.driveThickFirm = thickFirm
        driveMotorListe.driveAnaTetikleme = anaTetikleme
        driveMotorListe.driveIGBTtetikleme = igbtTetikleme
        driveMotorListe.driveTetiklemeBesPCB = tetiklemePCB
        driveMotorListe.driveNintBesPCB = nintBes
        driveMotorListe.drive24vBes = bes24v
        driveMotorListe.driveKontUnit = kontUnit
        driveMotorListe.driveFan = fan
        driveMotorListe.driveSigorta1 = sigorta1
        driveMotorListe.driveSigorta2 = sigorta2
        driveMotorListe.driveDegTarihi = uniteDegTarih
        driveMotorListe.driveMotorDegTarihi = motorDegTarihi

        motorListe.motorTag = tag
        motorListe.motorGucKW = guc.toDouble()
        motorListe.uniteDegTarihi = uniteDegTarih
        motorListe.seriNoU = seriNo
        motorListe.motorMCCYeri = "Drive MCC"
        motorListe.motorGelenVeri = "DriveUniteEkle"

        driveUniteRef.child(tag)
            .setValue(driveMotorListe)
            .addOnCompleteListener {

                if (it.isComplete){}else{

                    try {
                        Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }catch (hata : Exception){ }
                }
            }

        motorRef.child(tag)
            .setValue(motorListe)
            .addOnCompleteListener {

                if (it.isComplete){}else{

                    try {
                        Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }catch (hata : Exception){ }
                }
            }

    }

}
