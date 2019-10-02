package com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitEkle


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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayit
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitModel.AmbarKayitModeli
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.example.pm3elektrik.R
import com.example.pm3elektrik.Retrofit.FCMInterface
import com.example.pm3elektrik.Retrofit.FCMModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class AmbarKayitEkleme : DialogFragment() {

    val ambarListeEkle = AmbarKayitModeli()
    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
    var SERVER_KEY : String? = null
    val BASE_URL = "https://fcm.googleapis.com/fcm/"
    var kullaniciTokenleriArrayList = ArrayList<String>()
    lateinit var bildirim : FCMModel
    var kullaniciIsmi : String? = null
    var sicilNo : Int? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ambar_kayit_ekleme, container, false)

        val close = view.findViewById<ImageView>(R.id.imgAmbarEkleClose)
        val ekle = view.findViewById<Button>(R.id.buttonAmbarEkle)

        val bundle :Bundle? = arguments
        val stokNoBundle = bundle?.getString("rvGidenStokNo")
        val rafNoBundle= bundle?.getString("rvGidenRafNo")
        val tanimBundle = bundle?.getString("rvGidenTanim")

        serverKeyOku()

        kullaniciKayittanGelenIsimveSicilNo()

        val stokEditText = view.findViewById<EditText>(R.id.etAmbarStokNo)
        val rafEditText = view.findViewById<EditText>(R.id.etAmbarRafNo)
        val tanimEditText = view.findViewById<EditText>(R.id.etAmbarTanim)

        stokEditText.setText(stokNoBundle)
        rafEditText.setText(rafNoBundle)
        tanimEditText.setText(tanimBundle)

        close.setOnClickListener {
            dismiss()
        }
        ekle.setOnClickListener {

            val stokNo = view.findViewById<EditText>(R.id.etAmbarStokNo).text.toString()
            val rafNo = view.findViewById<EditText>(R.id.etAmbarRafNo).text.toString().toUpperCase()
            val tanim = view.findViewById<EditText>(R.id.etAmbarTanim).text.toString().toUpperCase()

            if (stokNo.isNotEmpty() && rafNo.isNotEmpty() && tanim.isNotEmpty()) {

                changeFragment(AmbarKayit())

                ambarListeEkle.ambarStokNo = stokNo
                ambarListeEkle.ambarRafNo = rafNo
                ambarListeEkle.ambarTanim = tanim

                val s1 = stokNo.substring(0..0)
                val s2 = stokNo.substring(2..stokNo.lastIndex)
                val stokNoSonHal = s1+s2

                ref.child("Ambar")
                    .child(stokNoSonHal)
                    .setValue(ambarListeEkle)
                    .addOnCompleteListener {

                        if(it.isComplete){

                        }else {
                            try {
                                Toast.makeText(activity,"Kayıt Yapılamadı Hata: ${it.exception?.message}",Toast.LENGTH_LONG).show()
                            }catch (hata : Exception){
                                Toast.makeText(activity,"Hata: ${hata.message}",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
            }

            fbDatabaseTokenlariAlveBildirimGonder(stokNo, tanim)
            Toast.makeText(activity,"Kayıt Başarılı",Toast.LENGTH_SHORT).show()
        }

        return view
    }
    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerAmbarKayit,fragment,"ambar_kayit_ekle_fr")
        fragmentTransaction.commit()

    }

    private fun fbDatabaseTokenlariAlveBildirimGonder(stokNumarasi: String, tanim: String) {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val myInterface = retrofit.create(FCMInterface::class.java)
        val headers = HashMap<String, String>()
        headers.put("Content-Type", "application/json")
        headers.put("Authorization", "key="+SERVER_KEY)

        val data = FCMModel.Data("$stokNumarasi $tanim","Eklendi/Düzenlendi","ambar",kullaniciIsmi!!,stokNumarasi)

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
