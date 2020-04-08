package com.example.pm3elektrik.TelefonListeSayfasi.TelefonEkleFragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel

import com.example.pm3elektrik.R
import com.example.pm3elektrik.Retrofit.FCMInterface
import com.example.pm3elektrik.Retrofit.FCMModel
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonListesi
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonModel.TelefonListeModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TelefonEkle : DialogFragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
    val telefonModel = TelefonListeModel()
    var SERVER_KEY : String? = null
    val BASE_URL = "https://fcm.googleapis.com/fcm/"
    var kullaniciTokenleriArrayList = ArrayList<String>()
    lateinit var bildirim : FCMModel
    var kullaniciIsmi : String? = null
    var sicilNo : Int? = 0



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_telefon_ekle, container, false)

        val buttonEkle = view.findViewById<Button>(R.id.buttonTelefonEkle)
        val buttonClose = view.findViewById<ImageView>(R.id.imgTelefonEkleClose)

        kullaniciKayittanGelenIsimveSicilNo()
        serverKeyOku()


        val bundle :Bundle? = arguments
        val isim = bundle?.getString("rvGidenTelIsim")
        val no = bundle?.getString("rvGidenTelNo")

        val telefonNo = view.findViewById<EditText>(R.id.etTelefonNo)
        val telefonIsim = view.findViewById<EditText>(R.id.etTelefonIsim)
        telefonIsim.setText(isim)
        telefonNo.setText(no)

        buttonEkle.setOnClickListener {


            telefonIsim.text.toString().toUpperCase()
            telefonNo.text.toString()

            if (telefonIsim.text.isNotEmpty() && telefonNo.text.isNotEmpty()) {

                telefonModel.telefonIsim = telefonIsim.text.toString().toUpperCase()
                telefonModel.telefonNo = telefonNo.text.toString()

                fbDatabaseTokenlariAlveBildirimGonder(
                    telefonNo.text.toString(),
                    telefonIsim.text.toString(),
                    kullaniciIsmi
                )

                changeFragment(TelefonListesi())

                ref.child("Telefon")
                    .child(telefonNo.text.toString())
                    .setValue(telefonModel)
                    .addOnCompleteListener {
                        if (it.isComplete) {

                        } else {
                            try {
                                Toast.makeText(
                                    activity,
                                    "Kayıt Yapılamadı Hata: ${it.exception?.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            } catch (hata: Exception) {
                                Toast.makeText(activity, "Hata: ${hata.message}", Toast.LENGTH_LONG)
                                    .show()
                            }


                        }
                    }


            } else {
                Toast.makeText(activity, "Boş Alanları Doldurunuz", Toast.LENGTH_LONG).show()
            }

            Toast.makeText(activity, "Kayıt Başarılı", Toast.LENGTH_SHORT).show()

    }



        buttonClose.setOnClickListener {

            dismiss()
        }

        return view
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerTelefonListesi,fragment,"telefon_ekle_fr")
        fragmentTransaction.commit()

    }

    private fun fbDatabaseTokenlariAlveBildirimGonder(telefonNo: String, telefonIsim: String?, kullaniciIsmi: String?) {

        Log.e("telefonIsimleri","$telefonNo $telefonIsim $kullaniciIsmi")

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val myInterface = retrofit.create(FCMInterface::class.java)
        val headers = HashMap<String, String>()
        headers.put("Content-Type", "application/json")
        headers.put("Authorization", "key="+SERVER_KEY)

        val data = FCMModel.Data("$telefonIsim Kişisine Ait","$telefonNo Nolu Telefon Eklendi","telefon", kullaniciIsmi!!,telefonNo)

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
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    SERVER_KEY = p0.getValue().toString()
                }
            })
    }

}
