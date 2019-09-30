package com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri


import android.os.Bundle
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
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel

import com.example.pm3elektrik.R
import com.example.pm3elektrik.Retrofit.FCMInterface
import com.example.pm3elektrik.Retrofit.FCMModel
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CekmeceEkle : Fragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Motor")
    val cekmeceListe = MotorModel()
    var uniqIDGelen: String? = null
    var SERVER_KEY : String? = null
    val BASE_URL = "https://fcm.googleapis.com/fcm/"
    var kullaniciTokenleriArrayList = ArrayList<String>()
    lateinit var bildirim : FCMModel
    var kullaniciIsmi : String? = null
    var sicilNo : Int? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cekmece_ekle, container, false)

        val close = view.findViewById<ImageView>(R.id.imgCekmeceEkleClose)
        val buttonEkle = view.findViewById<Button>(R.id.btnCekmeceSalterEkle)

        kullaniciKayittanGelenIsimveSicilNo()

        serverKeyOku()

        val bundle :Bundle? = arguments
        uniqIDGelen = bundle?.getString("salterOlanCekmecedenGelen")

        close.setOnClickListener {
            changeFragment(MotorListe())
        }

        buttonEkle.setOnClickListener {

            val isim = view.findViewById<EditText>(R.id.etCekmeceIsim).text.toString().toUpperCase()
            val marka = view.findViewById<EditText>(R.id.etCekmeceSalterIsmi).text.toString().toUpperCase()
            val model = view.findViewById<EditText>(R.id.etCekmeceSalterModel).text.toString().toUpperCase()
            val kapasite = view.findViewById<EditText>(R.id.etCekmeceSalterKapasite).text.toString()
            val cat = view.findViewById<EditText>(R.id.etCekmeceSalterCat).text.toString().toUpperCase()
            val degTarihi = view.findViewById<EditText>(R.id.etCekmeceSalterDegTarihi).text.toString()
            val demeraj = view.findViewById<EditText>(R.id.etCekmeceSalterDemeraj).text.toString().toUpperCase()
            val mccYeri = view.findViewById<EditText>(R.id.etCekmeceMccYeri).text.toString().toUpperCase()

            if (!isim.isNullOrEmpty() && !mccYeri.isNullOrEmpty()){
                firebaseDBEkle(isim,marka,model,kapasite,cat,degTarihi,demeraj,mccYeri)
            }else{
                Toast.makeText(view.context,"Çekmece İsim ve MCC Yerini Giriniz",Toast.LENGTH_SHORT).show()
            }
        }

        if (uniqIDGelen != null){

            firebaseOkunanBilgileriEdittexteIsle(uniqIDGelen!!, view)
        }
        return view
    }

    private fun firebaseOkunanBilgileriEdittexteIsle(motorTag: String , view : View) {

        ref.child(uniqIDGelen!!)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    val isim = view.findViewById<EditText>(R.id.etCekmeceIsim)
                    val marka = view.findViewById<EditText>(R.id.etCekmeceSalterIsmi)
                    val model = view.findViewById<EditText>(R.id.etCekmeceSalterModel)
                    val cat = view.findViewById<EditText>(R.id.etCekmeceSalterCat)
                    val kapasite = view.findViewById<EditText>(R.id.etCekmeceSalterKapasite)
                    val degTarihi = view.findViewById<EditText>(R.id.etCekmeceSalterDegTarihi)
                    val demeraj = view.findViewById<EditText>(R.id.etCekmeceSalterDemeraj)
                    val mccYeri = view.findViewById<EditText>(R.id.etCekmeceMccYeri)

                    if (p0.getValue() != null){
                        val okunan = p0.getValue(MotorModel::class.java)
                        if (okunan != null){

                            isim.setText(okunan.cekmeceIsim)
                            marka.setText(okunan.cekmeceMarka)
                            model.setText(okunan.cekmeceModel)
                            cat.setText(okunan.cekmeceCat)
                            kapasite.setText(okunan.cekmeceKapasite)
                            demeraj.setText(okunan.cekmeceDemeraj)
                            mccYeri.setText(okunan.motorMCCYeri)
                            degTarihi.setText(okunan.cekmeceSalterDegisim)
                        }
                    }


                }


            })

    }

    private fun firebaseDBEkle(isim: String, marka: String, model: String, kapasite: String, cat: String, degTarihi: String, demeraj: String , mccYeri : String) {

        val uniqueID = ref.push().key

        cekmeceListe.cekmeceIsim = isim
        cekmeceListe.cekmeceMarka = marka
        cekmeceListe.cekmeceModel = model
        cekmeceListe.cekmeceKapasite = kapasite
        cekmeceListe.cekmeceCat = cat
        cekmeceListe.cekmeceSalterDegisim = degTarihi
        cekmeceListe.cekmeceDemeraj = demeraj


        cekmeceListe.motorMCCYeri = mccYeri
        cekmeceListe.motorTag = isim
        cekmeceListe.motorGelenVeri = "cekmeceEkle"

        if (uniqIDGelen != null ){

            ref.child(uniqIDGelen!!)
                .setValue(cekmeceListe)
                .addOnCompleteListener { it->

                    if(it.isComplete){

                    }else{
                        try{
                            Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }catch (hata:Exception){ }
                    }
                }

            fbDatabaseTokenlariAlveBildirimGonder(isim,uniqIDGelen!!)

        }else {

            cekmeceListe.cekmeceUid = uniqueID!!
            ref.child(uniqueID)
                .setValue(cekmeceListe)
                .addOnCompleteListener { it->

                    if(it.isComplete){

                    }else{
                        try{
                            Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }catch (hata:Exception){ }
                    }
                }
            fbDatabaseTokenlariAlveBildirimGonder(isim,uniqueID)
        }
        Toast.makeText(view?.context,"Kayıt Başarılı",Toast.LENGTH_SHORT).show()
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorListe,fragment,"cekmece_ekle_fr")
        fragmentTransaction.commit()

    }

    private fun fbDatabaseTokenlariAlveBildirimGonder(gelenIsim: String, uniqIDGelen: String) {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val myInterface = retrofit.create(FCMInterface::class.java)
        val headers = HashMap<String, String>()
        headers.put("Content-Type", "application/json")
        headers.put("Authorization", "key="+SERVER_KEY)

        val data = FCMModel.Data("$gelenIsim TAG Nolu Çekmece","Eklendi/Düzenlendi","cekmece",kullaniciIsmi!!,uniqIDGelen)

        FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar").orderByKey()
            .addListenerForSingleValueEvent(object : ValueEventListener {
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
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    SERVER_KEY = p0.getValue().toString()
                }
            })
    }
}
