package com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.SalterModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.SurucuModel
import com.example.pm3elektrik.R
import com.example.pm3elektrik.Retrofit.FCMInterface
import com.example.pm3elektrik.Retrofit.FCMModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_motor_ekle.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.reflect.Array
import java.math.BigDecimal
import java.math.RoundingMode

class MotorEkle : Fragment() {

    val motor_liste = MotorModel()
    val salterListe = SalterModel()
    val surucuListe = SurucuModel()
    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
    var SERVER_KEY : String? = null
    val BASE_URL = "https://fcm.googleapis.com/fcm/"
    val gelenTOkenlar = ArrayList<String>()
    lateinit var bildirim : FCMModel

    companion object{
        var gucKW_static = 0.0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_motor_ekle, container, false)

        val button_close = view.findViewById<ImageView>(R.id.imgMotorCLose)
        val button_ekle = view.findViewById<Button>(R.id.buttonMotorEkle)

        val gucKw = view.findViewById<EditText>(R.id.etGucKw)
        val gucHp = view.findViewById<EditText>(R.id.etGucHP)

        serverKeyOku()

        gucKw.addTextChangedListener( object: TextWatcher {
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

        gucHp.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {

                if(!p0.isNullOrEmpty()){
                    val hp = gucHp.text.toString().toDouble()
                    val kw = hp*0.75
                    val kw_karsiligi =  BigDecimal(kw).setScale(1,RoundingMode.HALF_EVEN)
                    motor_liste.motorGucKW = kw_karsiligi.toDouble()
                    motor_liste.motorGucHP = hp

                    gucKW_static = kw_karsiligi.toDouble()

                }
            }
        })

        button_ekle.setOnClickListener {

            val motor_isim = view.findViewById<EditText>(R.id.etMotorIsim).text.toString().toUpperCase()
            val motor_tag = view.findViewById<EditText>(R.id.etMotorTag).text.toString().toUpperCase()
            val devir = view.findViewById<EditText>(R.id.etDevir).text.toString()
            val nom_trip_akimi = view.findViewById<EditText>(R.id.etNomTripAkimi).text.toString()
            val insa_tipi = view.findViewById<EditText>(R.id.etInsaTipi).text.toString().toUpperCase()
            val flans = view.findViewById<EditText>(R.id.etFlans).text.toString().toUpperCase()
            val adres = view.findViewById<EditText>(R.id.etMotorAdres).text.toString().toUpperCase()
            val mcc_yeri = view.findViewById<EditText>(R.id.etMotorMCCYeri).text.toString().toUpperCase()
            val degisim_tarihi = view.findViewById<EditText>(R.id.etMotorDegTarihi).text.toString()
            val motor_not = view.findViewById<EditText>(R.id.etMotorNot).text.toString().toUpperCase()


            if (motor_tag.isNotEmpty() && mcc_yeri.isNotEmpty()){


                    FirebaseDBMotorEkle(motor_isim ,motor_tag,devir,nom_trip_akimi,insa_tipi,flans,adres,mcc_yeri,degisim_tarihi,motor_not)

            }else{
                Toast.makeText(activity,"Lütfen Motor Tag ve Mcc Yerini Giriniz",Toast.LENGTH_LONG).show()
            }

            Toast.makeText(activity,"Kayıt Başarılı",Toast.LENGTH_SHORT).show()
        }

        button_close.setOnClickListener {
        changeFragment(MotorListe())
        }
        return view
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

    fun FirebaseDBMotorEkle(motorIsim : String , motorTag: String, motorDevir: String, motorNomTripAkimi: String,
                  motorInsaTipi: String, motorFlans: String, motorAdres: String, motorMCCYeri: String, motorDegTarihi: String , motorNot : String){



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

        salterListe.salterMotorTag = motorTag
        salterListe.salterMccYeri = motorMCCYeri
        salterListe.salterMarka = ""
        salterListe.salterDegTarihi = ""
        salterListe.salterDemeraj = ""
        salterListe.salterSTYLE = ""
        salterListe.salterCAT = ""
        salterListe.salterKapasite = ""

        surucuListe.surucuIsim = ""
        surucuListe.surucuDegTarihi = ""
        surucuListe.surucuModel = ""
        surucuListe.surucuDIPSivic = ""
        surucuListe.surucuBoyut = ""

        ref.child("Motor")
            .child(motorTag)
            .setValue(motor_liste).addOnCompleteListener {

                if(it.isSuccessful){

                }else{
                    try {
                        Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }catch (hata : Exception){ }
                }
            }


        //MOTOR EKLEDEN DİĞER KAYITLI KULLANICILARA BİLDİRİM GÖNDERİLİYOR---------------------------------------------->
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val myInterface = retrofit.create(FCMInterface::class.java)
        val headers = HashMap<String, String>()
        headers.put("Content-Type", "application/json")
        headers.put("Authorization", "key="+SERVER_KEY)

        val data = FCMModel.Data("$motorTag TAG Nolu Motor","Eklendi","$motorTag","")


        gelenTOkenlar.add("f0PIVT7dfJQ:APA91bHpx7_IBw6XvFD8tXbA20Q9nB7NwkItTOUdZu71ky9NZKNIEf-_SWSkYLttJeY7jHWKiE3t9lj0qOZaupbYH9QGkTgaRQcv4mNNiDKFFBZ9_kjXj89kbFC-brIO9hIjvjR3qLQl")
        gelenTOkenlar.add("frWhBrg3nNU:APA91bHmgarn1-fTXEjAqCuaMC2xzUnWEjDp4rz1Z2ZnGEonfmgfjpF2-GUj2CZrEIOeezs-VLAniixSfWw3PqgcUGEGWkCAWxu3ddHVe9Ns20kAtq4OtJUso5rABZFROkewXh9S0aum")
        gelenTOkenlar.add("cTHZIXuM9OY:APA91bHMZk7JgmmBD-glwlDK5z-1ySHbTD7qgYE-DUF2HnUSwWe-tzDvL7Vip40Fu4DXPfDAGwXINMDyfV12wq_auoMlIV13ycrZ8c6xOp-2iovDomaiShn_gaCTijb_jbWaFj5QaNO6")

        for (index in 0..2){
            bildirim = FCMModel(gelenTOkenlar.get(index), data)

            val istek = myInterface.bildirimGonder(headers,bildirim)


            istek.enqueue(object : Callback<Response<FCMModel>>{
                override fun onFailure(call: Call<Response<FCMModel>>, t: Throwable) {

                    Log.e("BAŞARISIZ","Hata: ${t.message}")
                }

                override fun onResponse(call: Call<Response<FCMModel>>, response: Response<Response<FCMModel>>) {

                    Log.e("BAŞARILI","Gönderildi: $response")


                }


            })
        }







        //MOTOR EKLEDEN DİĞER KAYITLI KULLANICILARA BİLDİRİM GÖNDERİLİYOR----------------------------------------------<

        ref.child("Salter")
            .child(motorTag)
            .setValue(salterListe).addOnCompleteListener {
                if(it.isSuccessful){

                }else{
                    try {
                        Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }catch (hata : Exception){ }
                }
            }

        ref.child("Surucu")
            .child(motorTag)
            .setValue(surucuListe).addOnCompleteListener {
                if(it.isSuccessful){

                }else{
                    try {
                        Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }catch (hata : Exception){ }
                }
            }
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorListe,fragment,"motor_ekle_fr")
        fragmentTransaction.commit()

    }

}
