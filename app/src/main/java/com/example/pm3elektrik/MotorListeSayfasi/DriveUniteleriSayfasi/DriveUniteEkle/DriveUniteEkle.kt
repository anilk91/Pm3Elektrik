package com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteEkle


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteModel.DriveModel
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

            val isim = view.findViewById<EditText>(R.id.etDriveMotorIsim).text.toString().toUpperCase()
            val tag = view.findViewById<EditText>(R.id.etDriveMotorTag).text.toString().toUpperCase()
            val guc = view.findViewById<EditText>(R.id.etDriveMotorGuc).text.toString()
            val devir = view.findViewById<EditText>(R.id.etDriveMotorDevir).text.toString()
            val tripAkim = view.findViewById<EditText>(R.id.etDriveMotorTripAkimi).text.toString()
            val insaTipi = view.findViewById<EditText>(R.id.etDriveMotorInsaTipi).text.toString().toUpperCase()
            val flans = view.findViewById<EditText>(R.id.etDriveMotorFlans).text.toString().toUpperCase()
            val adres = view.findViewById<EditText>(R.id.etDriveMotorAdres).text.toString().toUpperCase()
            val seriNoU = view.findViewById<EditText>(R.id.etDriveUniteSeriNoU).text.toString()
            val seriNoV = view.findViewById<EditText>(R.id.etDriveUniteSeriNoV).text.toString()
            val seriNoW = view.findViewById<EditText>(R.id.etDriveUniteSeriNoW).text.toString()
            val motorDegTarihi = view.findViewById<EditText>(R.id.etDriveMotorDegTarihi).text.toString()

            //firebaseDBEkle(tag,guc,akim,voltaj,modul,code,seriNo,igbt,kondansator,thickFirm,anaTetikleme,igbtTetikleme,tetiklemePCB,nintBes,bes24v,kontUnit,
                //fan,sigorta1,sigorta2,uniteDegTarih,motorDegTarihi)
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
        motorListe.motorGucKVA = guc
        motorListe.motorDevir = "1000"
        motorListe.motorMCCYeri = "DRİVE MCC"
        motorListe.motorGelenVeri = "driveMotorEkle"


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
