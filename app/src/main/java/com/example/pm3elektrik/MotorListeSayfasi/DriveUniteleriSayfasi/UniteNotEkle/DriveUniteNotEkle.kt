package com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.UniteNotEkle

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
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUnite
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteModel.UniteNotuModel

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
import java.text.SimpleDateFormat
import java.util.*


class DriveUniteNotEkle : DialogFragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("DriveUniteNot")
    val notListesi = UniteNotuModel()
    var motorTag: String? = null
    var SERVER_KEY : String? = null
    val BASE_URL = "https://fcm.googleapis.com/fcm/"
    var kullaniciTokenleriArrayList = ArrayList<String>()
    lateinit var bildirim : FCMModel
    var kullaniciIsmi : String? = null
    var sicilNo : Int? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drive_unite_not_ekle, container, false)

        val close = view.findViewById<ImageView>(R.id.imgDriveUniteNotEkleClose)
        val ekle = view.findViewById<Button>(R.id.btnDriveUniteNotEkle)

        val bundle: Bundle? = arguments
        motorTag = bundle?.getString("driveUniteGelenTag")

        serverKeyOku()

        kullaniciKayittanGelenIsimveSicilNo()

        close.setOnClickListener { dismiss() }

        ekle.setOnClickListener {

            val not = view.findViewById<EditText>(R.id.etDriveUniteNotEkle).text.toString()
            val tarih = view.findViewById<EditText>(R.id.etDriveUniteTarihEkle).text.toString()

            notListesi.uniteNotu = not
            notListesi.uniteNotuTarih = tarih
            notListesi.sistemSaat = saat()



            if (not.isNotEmpty() && tarih.isNotEmpty()) {
                ref.child(motorTag!!).child(saat()).setValue(notListesi)
                    .addOnCompleteListener {
                        if (it.isComplete) {

                        } else {
                            try {
                                Toast.makeText(activity, "Kayıt Yapılamadı Hata: ${it.exception?.message}", Toast.LENGTH_LONG).show()
                            } catch (hata: Exception) {
                                Toast.makeText(activity, "Hata: ${hata.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                fbDatabaseTokenlariAlveBildirimGonder(motorTag!!)

                Toast.makeText(activity, "Başarılı", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(activity, "Boş Alanları Doldurunuz", Toast.LENGTH_LONG).show()
            }

            degistir(DriveUnite())
        }
        return view
    }

    private fun saat(): String {

        val lokalZaman = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MMMM EEEE HH:mm:ss", Locale("tr"))
        val zaman = formatter.format(lokalZaman)

        return zaman

    }

    private fun degistir(fragment : Fragment){

        val bundle : Bundle? =Bundle()
        bundle?.putString("rvGelenMotorTag",motorTag)
        val frDrive = DriveUnite()
        frDrive.arguments = bundle
        val transaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.containerDriveUnite,frDrive,"drive_unite_fr")?.commit()
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

        val data = FCMModel.Data("$motorTag TAG Nolu Drive Ünite Notu","Eklendi/Düzenlendi","drive",kullaniciIsmi!!,motorTag)

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
