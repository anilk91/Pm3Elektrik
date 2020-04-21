package com.example.pm3elektrik.MotorListeSayfasi.DuzenleFragmentleri

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.R
import com.example.pm3elektrik.Retrofit.FCMInterface
import com.example.pm3elektrik.Retrofit.FCMModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_motor_ekle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode

class MotorEtiketDuzenle : Fragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Motor")
    val motor_liste = MotorModel()
    var SERVER_KEY : String? = null
    val BASE_URL = "https://fcm.googleapis.com/fcm/"
    var kullaniciTokenleriArrayList = ArrayList<String>()
    lateinit var bildirim : FCMModel
    var kullaniciIsmi : String? = null
    var sicilNo : Int? = 0
    var motorDuzenlemeYetki = "yok"

    companion object{
        var gucKW_static = 0.0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_motor_ekle, container, false)

        val bundle :Bundle? = arguments
        val motorTag = bundle?.getString("motorEtiketDuzenle")

        kullaniciBilgileriniOku()
        serverKeyOku()

        kullaniciKayittanGelenIsimveSicilNo()

        if(motorTag != null){
            firebaseOkunan(motorTag)
        }

        val button_close = view.findViewById<ImageView>(R.id.imgMotorCLose)
        val button_ekle = view.findViewById<Button>(R.id.buttonMotorEkle)

        val gucKw = view.findViewById<EditText>(R.id.etGucKw)
        val gucHp = view.findViewById<EditText>(R.id.etGucHP)

        gucKw.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {

                if(!p0.isNullOrBlank()){
                    val kw = gucKw.text.toString().toDouble()
                    val hp = (kw/0.75)
                    val hp_karsiligi = BigDecimal(hp).setScale(1,RoundingMode.HALF_EVEN)
                    motor_liste.motorGucHP = hp_karsiligi.toDouble()
                    motor_liste.motorGucKW = kw
                    gucKW_static = kw
                }
            }
        })

        gucHp.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {

               if(!p0.isNullOrEmpty()){
                   val hp = gucHp.text.toString().toDouble()
                   val kw = hp*0.75
                   val kw_karsiligi =  BigDecimal(kw).setScale(1, RoundingMode.HALF_EVEN)
                   motor_liste.motorGucKW = kw_karsiligi.toDouble()
                   motor_liste.motorGucHP = hp
                   gucKW_static = kw_karsiligi.toDouble()
               }
            }
        })

        button_ekle.setOnClickListener {

            if (motorDuzenlemeYetki == "var") {
                if (etMotorTag.text.toString().isNotEmpty() && etMotorMCCYeri.text.toString()
                        .isNotEmpty()
                ) {

                    val motor_isim =
                        view.findViewById<EditText>(R.id.etMotorIsim).text.toString().toUpperCase()
                    val motor_tag =
                        view.findViewById<EditText>(R.id.etMotorTag).text.toString().toUpperCase()
                    val devir = view.findViewById<EditText>(R.id.etDevir).text.toString()
                    val nom_trip_akimi =
                        view.findViewById<EditText>(R.id.etNomTripAkimi).text.toString()
                    val insa_tipi =
                        view.findViewById<EditText>(R.id.etInsaTipi).text.toString().toUpperCase()
                    val flans =
                        view.findViewById<EditText>(R.id.etFlans).text.toString().toUpperCase()
                    val adres =
                        view.findViewById<EditText>(R.id.etMotorAdres).text.toString().toUpperCase()
                    val mcc_yeri = view.findViewById<EditText>(R.id.etMotorMCCYeri).text.toString()
                        .toUpperCase()
                    val degisim_tarihi =
                        view.findViewById<EditText>(R.id.etMotorDegTarihi).text.toString()
                    val motor_not =
                        view.findViewById<EditText>(R.id.etMotorNot).text.toString().toUpperCase()


                    FirebaseDBMotorEkle(
                        motor_isim,
                        motor_tag,
                        devir,
                        nom_trip_akimi,
                        insa_tipi,
                        flans,
                        adres,
                        mcc_yeri,
                        degisim_tarihi,
                        motor_not
                    )

                } else {
                    Toast.makeText(
                        activity,
                        "Lütfen Motor Tag ve Mcc Yerini Giriniz",
                        Toast.LENGTH_LONG
                    ).show()
                }
                Toast.makeText(activity, "Kayıt Başarılı", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(activity, "Motor düzenleme yetkiniz yok!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }



    fun FirebaseDBMotorEkle(motorIsim: String, motorTag: String, motorDevir: String, motorNomTripAkimi: String,
        motorInsaTipi: String, motorFlans: String, motorAdres: String, motorMCCYeri: String, motorDegTarihi: String, motorNot : String) {

        motor_liste.motorIsim = motorIsim
        motor_liste.motorTag = motorTag
        motor_liste.motorDevir = motorDevir
        motor_liste.motorNomTripAkimi = motorNomTripAkimi
        motor_liste.motorInsaTipi = motorInsaTipi
        motor_liste.motorFlans = motorFlans
        motor_liste.motorAdres = motorAdres
        motor_liste.motorMCCYeri = motorMCCYeri
        motor_liste.motorDegTarihi = motorDegTarihi
        motor_liste.motorNot = motorNot
        motor_liste.motorGelenVeri = "motorEkle"

        ref.child(motorTag)
            .setValue(motor_liste).addOnCompleteListener {

                if (it.isSuccessful) { } else {
                    try {
                        Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }catch (hata : Exception){ }

                }
            }

        fbDatabaseTokenlariAlveBildirimGonder(motorTag)
    }

    private fun firebaseOkunan(gelenMotorTag: String?) {

        ref.child(gelenMotorTag!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {

                    val okunan = p0.getValue(MotorModel::class.java)
                    if (okunan != null) {
                        etMotorIsim.setText(okunan.motorIsim)
                        etMotorTag.setText(okunan.motorTag)
                        etGucKw.setText("${okunan.motorGucKW}")
                        etGucHP.setText("${okunan.motorGucHP}")
                        etDevir.setText(okunan.motorDevir)
                        etNomTripAkimi.setText(okunan.motorNomTripAkimi)
                        etInsaTipi.setText(okunan.motorInsaTipi)
                        etFlans.setText(okunan.motorFlans)
                        etMotorAdres.setText(okunan.motorAdres)
                        etMotorMCCYeri.setText(okunan.motorMCCYeri)
                        etMotorDegTarihi.setText(okunan.motorDegTarihi)
                        etMotorNot.setText(okunan.motorNot)
                    }

                }

            })

    }

    private fun fbDatabaseTokenlariAlveBildirimGonder(motorTag : String) {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val myInterface = retrofit.create(FCMInterface::class.java)
        val headers = HashMap<String, String>()
        headers.put("Content-Type", "application/json")
        headers.put("Authorization", "key="+SERVER_KEY)

        val data = FCMModel.Data("$motorTag TAG Nolu Motor","Düzenlendi","motor",kullaniciIsmi!!,motorTag)

        FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar").orderByKey()
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    for(tumKullaniciSicilNo in p0.children){

                        val gelenSiciller = tumKullaniciSicilNo.getValue(KullaniciModel::class.java)

                        if (gelenSiciller != null){


                            if (!gelenSiciller.sicilNo.equals(sicilNo)){
                                kullaniciTokenleriArrayList.add(gelenSiciller.kullaniciToken)
                            }
                        }

                    }

                    for (index in 0..kullaniciTokenleriArrayList.size-1){
                        bildirim = FCMModel(kullaniciTokenleriArrayList.get(index), data)

                        val istek = myInterface.bildirimGonder(headers,bildirim)
                        istek.enqueue(object : Callback<Response<FCMModel>> {
                            override fun onFailure(call: Call<Response<FCMModel>>, t: Throwable) {

                                Log.e("BAŞARISIZ","Hata: ${t.message}")
                            }

                            override fun onResponse(call: Call<Response<FCMModel>>, response: Response<Response<FCMModel>>) {

                                Log.e("BAŞARILI","Gönderildi: $response")


                            }


                        })
                    }
                    kullaniciTokenleriArrayList.clear()
                }
            })
    }

    private fun kullaniciKayittanGelenIsimveSicilNo(){

        val sharedPreferences = context?.getSharedPreferences("gelenKullaniciIsmi",0)
        val isim = sharedPreferences?.getString("KEY_ISIM","")
        val sicil = sharedPreferences?.getInt("KEY_SICIL_NO",0)
        kullaniciIsmi = isim
        sicilNo = sicil
    }

    private fun serverKeyOku() {

        FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Server").child("server_key")
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    SERVER_KEY = p0.getValue().toString()
                }
            })
    }

    private fun kullaniciBilgileriniOku() {

        val sharedPreferences = activity?.getSharedPreferences("gelenKullaniciBilgileri", 0)
        motorDuzenlemeYetki = sharedPreferences?.getString("KEY_MOTOR_YETKI","").toString()

    }
}







