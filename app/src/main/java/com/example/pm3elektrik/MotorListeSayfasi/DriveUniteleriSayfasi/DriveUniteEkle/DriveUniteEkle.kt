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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_ana_sayfa.*
import kotlinx.android.synthetic.main.fragment_drive_unite_ekle.*
import java.lang.Exception

class DriveUniteEkle : Fragment() {


    val driveMotorListe = DriveModel()
    val motorListe = MotorModel()
    val driveUniteRef = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Drive")
    val motorRef = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Motor")
    val uniteSecim = arrayOf("Ünite Kapasite Seçiniz","20 KVA","60 KVA","180 KVA","250 KVA","490 KVA","600 KVA","900 KVA","1040 KVA","1380 KVA")
    var motorTag: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drive_unite_ekle, container, false)

        val ekle = view.findViewById<Button>(R.id.buttonDriveEkle)
        val close = view.findViewById<ImageView>(R.id.imgDriveUniteEkleClose)
        val spinnerSecim = view.findViewById<Spinner>(R.id.spinnerDriveUniteSecim)

        val bundle :Bundle? = arguments
        motorTag = bundle?.getString("driveUniteGelenTag")

        if (!motorTag.isNullOrEmpty()){

            firebaseOkunanVerileriEdittexteIsle(view)
        }

        spinnerSecim.adapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_dropdown_item,uniteSecim)
        spinnerSecim.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if (p2 == 0){

                    etDriveUniteSeriNoU.visibility = View.GONE
                    etDriveUniteUDegTarihi.visibility = View.GONE

                    etDriveUniteSeriNoW.visibility = View.GONE
                    etDriveUniteWDegTarihi.visibility = View.GONE

                    etDriveUniteSeriNoV.visibility = View.GONE
                    etDriveUniteVDegTarihi.visibility = View.GONE

                }

                else if (p2 == 1 || p2 == 2 || p2 == 3 || p2 == 4 || p2 == 5){

                    if (p2 == 1){ driveMotorListe.uniteGucKVA = "20"}
                    else if (p2 == 2){ driveMotorListe.uniteGucKVA = "60"}
                    else if (p2 == 3){ driveMotorListe.uniteGucKVA = "180"}
                    else if (p2 == 4){ driveMotorListe.uniteGucKVA = "250"}
                    else if (p2 == 5){ driveMotorListe.uniteGucKVA = "490"}

                    etDriveUniteSeriNoU.visibility = View.VISIBLE
                    etDriveUniteUDegTarihi.visibility = View.VISIBLE

                    etDriveUniteSeriNoU.setHint("Seri No")
                    etDriveUniteUDegTarihi.setHint("Modül Değ. Tarihi")

                    etDriveUniteSeriNoW.visibility = View.GONE
                    etDriveUniteWDegTarihi.visibility = View.GONE

                    etDriveUniteSeriNoV.visibility = View.GONE
                    etDriveUniteVDegTarihi.visibility = View.GONE

                    driveMotorListe.uniteGucKVA

                }
                else if (p2 == 6 || p2 == 7 || p2 == 8 || p2 == 9){

                    if (p2 == 6){ driveMotorListe.uniteGucKVA = "600"}
                    else if (p2 == 7){ driveMotorListe.uniteGucKVA = "900"}
                    else if (p2 == 8){ driveMotorListe.uniteGucKVA = "1040"}
                    else if (p2 == 9){ driveMotorListe.uniteGucKVA = "1380"}


                    etDriveUniteSeriNoU.visibility = View.VISIBLE
                    etDriveUniteUDegTarihi.visibility = View.VISIBLE
                    etDriveUniteSeriNoU.setHint("Seri No (U)")
                    etDriveUniteUDegTarihi.setHint("U Modül Değ. Tarihi")

                    etDriveUniteSeriNoW.visibility = View.VISIBLE
                    etDriveUniteWDegTarihi.visibility = View.VISIBLE

                    etDriveUniteSeriNoV.visibility = View.VISIBLE
                    etDriveUniteVDegTarihi.visibility = View.VISIBLE

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

    private fun firebaseOkunanVerileriEdittexteIsle(view: View) {

        driveUniteRef.child(motorTag!!)
            .addListenerForSingleValueEvent(object  : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    val isim = view.findViewById<EditText>(R.id.etDriveMotorIsim)
                    val tag = view.findViewById<EditText>(R.id.etDriveMotorTag)
                    val guc = view.findViewById<EditText>(R.id.etDriveMotorGuc)
                    val devir = view.findViewById<EditText>(R.id.etDriveMotorDevir)
                    val tripAkim = view.findViewById<EditText>(R.id.etDriveMotorTripAkimi)
                    val insaTipi = view.findViewById<EditText>(R.id.etDriveMotorInsaTipi)
                    val flans = view.findViewById<EditText>(R.id.etDriveMotorFlans)
                    val adres = view.findViewById<EditText>(R.id.etDriveMotorAdres)
                    val motorDegTarihi = view.findViewById<EditText>(R.id.etDriveMotorDegTarihi)
                    val seriNoU = view.findViewById<EditText>(R.id.etDriveUniteSeriNoU)
                    val seriNoV = view.findViewById<EditText>(R.id.etDriveUniteSeriNoV)
                    val seriNoW = view.findViewById<EditText>(R.id.etDriveUniteSeriNoW)
                    val uModulDegTarih = view.findViewById<EditText>(R.id.etDriveUniteUDegTarihi)
                    val vModulDegTarih = view.findViewById<EditText>(R.id.etDriveUniteVDegTarihi)
                    val wModulDegTarih = view.findViewById<EditText>(R.id.etDriveUniteWDegTarihi)


                    val gelenBilgiler = p0.getValue(DriveModel::class.java)

                    if(gelenBilgiler != null){

                        isim.setText(gelenBilgiler.isim)
                        tag.setText(gelenBilgiler.tag)
                        guc.setText("${gelenBilgiler.guc}")
                        devir.setText(gelenBilgiler.devir)
                        tripAkim.setText(gelenBilgiler.tripAkim)
                        insaTipi.setText(gelenBilgiler.insaTipi)
                        flans.setText(gelenBilgiler.flans)
                        adres.setText(gelenBilgiler.adres)
                        motorDegTarihi.setText(gelenBilgiler.motorDegTarihi)
                        seriNoU.setText(gelenBilgiler.seriNoU)
                        seriNoV.setText(gelenBilgiler.seriNoV)
                        seriNoW.setText(gelenBilgiler.seriNoW)
                        uModulDegTarih.setText(gelenBilgiler.uModulDegTarihi)
                        vModulDegTarih.setText(gelenBilgiler.vModulDegTarihi)
                        wModulDegTarih.setText(gelenBilgiler.wModulDegTarihi)


                    }



                }


            })

    }

    private fun firebaseDBEkle(isim: String, tag: String, guc: String, devir: String, tripAkim: String, insaTipi: String, flans: String, adres: String,
        motorDegTarihi: String, seriNoU: String, seriNoV: String, seriNoW: String, uModulDegTarih: String, vModulDegTarih: String, wModulDegTarih: String
    ) {

        if (!guc.isNullOrBlank()){
            motorListe.motorGucKW = guc.toDouble()
            driveMotorListe.guc = guc.toDouble()
        }else{
            motorListe.motorGucKW = 0.0
            driveMotorListe.guc = 0.0
        }
        driveMotorListe.isim = isim
        driveMotorListe.tag = tag

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

        motorListe.motorDevir = devir
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

        Toast.makeText(activity,"Kayıt Başarılı",Toast.LENGTH_SHORT).show()

    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.containerMotorListe,fragment,"fragment_drive_unite_ekle")
        fragmentTransaction?.commit()
    }

}
