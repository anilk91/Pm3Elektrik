package com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteEkle


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteModel.DriveModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel

import com.example.pm3elektrik.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_drive_unite_ekle.*
import java.lang.Exception

class DriveUniteEkle : Fragment() {


    val driveMotorListe = DriveModel()
    val motorListe = MotorModel()
    val driveUniteRef = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Drive")
    val motorRef = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Motor")
    val uniteSecim = arrayOf("Ünite Kapasite Seçiniz","180 KVA","490 KVA","900 KVA","1040 KVA")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drive_unite_ekle, container, false)

        val ekle = view.findViewById<Button>(R.id.buttonDriveEkle)
        val close = view.findViewById<ImageView>(R.id.imgDriveUniteEkleClose)
        val spinnerSecim = view.findViewById<Spinner>(R.id.spinnerDriveUniteSecim)

        spinnerSecim.adapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_dropdown_item,uniteSecim)
        spinnerSecim.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if (p2 == 1){

                    etDriveUniteSeriNoU.setHint("Seri No")
                    etDriveUniteUDegTarihi.setHint("Modül Değ. Tarihi")

                    etDriveUniteSeriNoW.visibility = View.GONE
                    etDriveUniteWDegTarihi.visibility = View.GONE

                    etDriveUniteSeriNoV.visibility = View.GONE
                    etDriveUniteVDegTarihi.visibility = View.GONE

                }
                else if (p2 == 2){

                }
                else if (p2 == 3){

                }

            }


        }

        close.setOnClickListener {
            changeFragment(MotorListe())
        }

        ekle.setOnClickListener {

            val isim = view.findViewById<EditText>(R.id.etDriveMotorIsim).text.toString().toUpperCase()
            val tag = view.findViewById<EditText>(R.id.etDriveMotorTag).text.toString().toUpperCase()
            val guc = view.findViewById<EditText>(R.id.etDriveMotorGuc).text.toString()
            val devir = view.findViewById<EditText>(R.id.etDriveMotorDevir).text.toString()
            val tripAkim = view.findViewById<EditText>(R.id.etDriveMotorTripAkimi).text.toString()
            val insaTipi = view.findViewById<EditText>(R.id.etDriveMotorInsaTipi).text.toString().toUpperCase()
            val flans = view.findViewById<EditText>(R.id.etDriveMotorFlans).text.toString().toUpperCase()
            val adres = view.findViewById<EditText>(R.id.etDriveMotorAdres).text.toString().toUpperCase()
            val motorDegTarihi = view.findViewById<EditText>(R.id.etDriveMotorDegTarihi).text.toString()
            val seriNoU = view.findViewById<EditText>(R.id.etDriveUniteSeriNoU).text.toString()
            val seriNoV = view.findViewById<EditText>(R.id.etDriveUniteSeriNoV).text.toString()
            val seriNoW = view.findViewById<EditText>(R.id.etDriveUniteSeriNoW).text.toString()
            val uModulDegTarih = view.findViewById<EditText>(R.id.etDriveUniteUDegTarihi).text.toString()
            val vModulDegTarih = view.findViewById<EditText>(R.id.etDriveUniteVDegTarihi).text.toString()
            val wModulDegTarih = view.findViewById<EditText>(R.id.etDriveUniteWDegTarihi).text.toString()

            firebaseDBEkle(isim, tag, guc, devir, tripAkim, insaTipi, flans, adres, motorDegTarihi, seriNoU, seriNoV, seriNoW, uModulDegTarih,
                vModulDegTarih, wModulDegTarih)
        }
        return view
    }

    private fun firebaseDBEkle(isim: String, tag: String, guc: String, devir: String, tripAkim: String, insaTipi: String, flans: String, adres: String,
        motorDegTarihi: String, seriNoU: String, seriNoV: String, seriNoW: String, uModulDegTarih: String, vModulDegTarih: String, wModulDegTarih: String
    ) {

        driveMotorListe.isim = isim
        driveMotorListe.tag = tag
        driveMotorListe.guc = guc
        driveMotorListe.devir = devir
        driveMotorListe.tripAkim = tripAkim
        driveMotorListe.insaTipi = insaTipi
        driveMotorListe.flans = flans
        driveMotorListe.adres = adres
        driveMotorListe.motorDegTarihi = motorDegTarihi
        driveMotorListe.seriNoU = seriNoU
        driveMotorListe.seriNoV = seriNoV
        driveMotorListe.seriNoW = seriNoW
        driveMotorListe.uModulDegTarihi = uModulDegTarih
        driveMotorListe.vModulDegTarihi = vModulDegTarih
        driveMotorListe.wModulDegTarihi = wModulDegTarih


        motorListe.motorTag = tag
        motorListe.motorGucKW = guc.toDouble()
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

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.containerMotorListe,fragment,"fragment_drive_unite_ekle")
        fragmentTransaction?.commit()
    }

}
