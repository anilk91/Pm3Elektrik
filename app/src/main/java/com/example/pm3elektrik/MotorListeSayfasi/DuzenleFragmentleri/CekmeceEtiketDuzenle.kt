package com.example.pm3elektrik.MotorListeSayfasi.DuzenleFragmentleri

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.SalterModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.SurucuModel
import com.example.pm3elektrik.R
import com.example.pm3elektrik.Retrofit.FCMInterface
import com.example.pm3elektrik.Retrofit.FCMModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_salter_ekle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class CekmeceEtiketDuzenle : Fragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
    val secimListesi = arrayOf("Sürücü Seçiniz", "Kontaktör", "Frekans Konvertörü", "IT SoftStarter", "EasySoftStarter")
    val salter_liste = SalterModel()
    val surucu_liste = SurucuModel()
    lateinit var surucuSpinner : Spinner
    var SERVER_KEY : String? = null
    val BASE_URL = "https://fcm.googleapis.com/fcm/"
    var kullaniciTokenleriArrayList = ArrayList<String>()
    lateinit var bildirim : FCMModel
    var kullaniciIsmi : String? = null
    var sicilNo : Int? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_salter_ekle,container,false)

        val bundle :Bundle? = arguments
        val motorTag = bundle?.getString("cekmeceEtiketDuzenle")

        serverKeyOku()

        kullaniciKayittanGelenIsimveSicilNo()

        if(motorTag != null){
            firebaseDBOkunanVeriler(motorTag)
        }

        val buttonClose = view.findViewById<ImageView>(R.id.imgSalterClose)
        val buttonEkle = view.findViewById<Button>(R.id.buttonSalterEkle)
        surucuSpinner = view.findViewById(R.id.spinnerSurucuSecim) as Spinner

        spinnerSecim(container , view)

        buttonEkle.setOnClickListener {

            val motor_tag = etMotorTag.text.toString().toUpperCase()
            val marka = etSalterMarka.text.toString().toUpperCase()
            val kapasite = etSalterKapasite.text.toString()
            val cat = etSalterCAT.text.toString().toUpperCase()
            val style = etSalterSTYLE.text.toString().toUpperCase()
            val demeraj = etSalterDemeraj.text.toString().toUpperCase()
            val degisimTarihi = etSalterDegTarihi.text.toString()
            val mccYeri = etSalterMCCYeri.text.toString().toUpperCase()
            val cekmeceDegTarihi = etCekmeceDegTarihi.text.toString()

            val dipSivic = etSalterKontaktorDIPSivic.text.toString().toUpperCase()
            val kontaktorBoyut = etSalterKontaktorBoyut.text.toString().toUpperCase()
            val surucuModel = etSalterSurucuModel.text.toString().toUpperCase()
            val surucuDegisimTarihi = etSalterSurucuDegTarihi.text.toString().toUpperCase()




            if (motor_tag.isNotEmpty()) {

                if(!dipSivic.isNullOrEmpty() && !dipSivic.isNullOrBlank()){
                    if(dipSivic.length <= 7){
                        Toast.makeText(view.context,"Dip Siviç değeri 8 rakamdan az",Toast.LENGTH_SHORT).show()
                    }else{
                        surucu_liste.surucuDIPSivic = dipSivic
                    }
                }

                salter_liste.salterMotorTag = motor_tag
                salter_liste.salterMarka = marka
                salter_liste.salterKapasite = kapasite
                salter_liste.salterCAT = cat
                salter_liste.salterSTYLE = style
                salter_liste.salterDemeraj = demeraj
                salter_liste.salterDegTarihi = degisimTarihi
                salter_liste.salterMccYeri = mccYeri
                salter_liste.cekmeceDegTarihi = cekmeceDegTarihi

                surucu_liste.surucuBoyut = kontaktorBoyut

                surucu_liste.surucuDegTarihi = surucuDegisimTarihi
                surucu_liste.surucuModel = surucuModel

                ref.child("Salter")
                    .child(motor_tag)
                    .setValue(salter_liste)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {

                        } else {
                            try {
                                Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                            }catch (hata : Exception){ }
                        }
                    }
                ref.child("Surucu")
                    .child(motor_tag)
                    .setValue(surucu_liste)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {

                        } else {
                            try {
                                Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                            }catch (hata : Exception){ }
                        }

                    }

            } else {
                Toast.makeText(activity, "Motor Tag Alanını Doldurunuz... ", Toast.LENGTH_LONG).show()
            }

            fbDatabaseTokenlariAlveBildirimGonder(motorTag!!)
            Toast.makeText(activity,"Kayıt Başarılı",Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun spinnerSecim(container: ViewGroup?, viewContext: View) {

                surucuSpinner.adapter =
                    ArrayAdapter(container!!.context, android.R.layout.simple_spinner_dropdown_item, secimListesi)
                surucuSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

                                if (position == 1) {

                                    etSalterSurucuModel.visibility = View.VISIBLE
                                    etSalterKontaktorDIPSivic.visibility = View.VISIBLE
                                    etSalterKontaktorBoyut.visibility = View.VISIBLE
                                    etSalterSurucuDegTarihi.visibility = View.VISIBLE

                                    surucu_liste.surucuIsim = "Kontaktör"

                                } else if (position == 2) {


                                    etSalterKontaktorDIPSivic.visibility = View.GONE
                                    etSalterKontaktorBoyut.visibility = View.GONE

                                    etSalterSurucuModel.visibility = View.VISIBLE
                                    etSalterSurucuDegTarihi.visibility = View.VISIBLE

                                    surucu_liste.surucuIsim = "Frekans Konvertörü"

                                } else if (position == 3) {


                                    etSalterKontaktorDIPSivic.visibility = View.GONE
                                    etSalterKontaktorBoyut.visibility = View.GONE

                                    etSalterSurucuModel.visibility = View.VISIBLE
                                    etSalterSurucuDegTarihi.visibility = View.VISIBLE

                                    surucu_liste.surucuIsim = "IT SoftStarter"


                                } else if (position == 4) {


                                    etSalterKontaktorDIPSivic.visibility = View.GONE
                                    etSalterKontaktorBoyut.visibility = View.GONE

                                    etSalterSurucuModel.visibility = View.VISIBLE
                                    etSalterSurucuDegTarihi.visibility = View.VISIBLE

                                    etSalterKontaktorBoyut.editableText.clear()
                                    etSalterKontaktorDIPSivic.editableText.clear()

                                    surucu_liste.surucuIsim = "Easy SoftStarter"


                                } else if (position == 0) {

                                    etSalterKontaktorDIPSivic.visibility = View.GONE
                                    etSalterKontaktorBoyut.visibility = View.GONE
                                    etSalterSurucuModel.visibility = View.GONE
                                    etSalterSurucuDegTarihi.visibility = View.GONE
                                }
                    }
                }

    }

    private fun firebaseDBOkunanVeriler(gelenMotorTag: String) {

        ref.child("Salter")
            .child(gelenMotorTag)
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {


                    val okunan = p0.getValue(SalterModel::class.java)
                    if(okunan != null){
                        etMotorTag.setText(okunan.salterMotorTag)
                        etSalterMarka.setText(okunan.salterMarka)
                        etSalterKapasite.setText(okunan.salterKapasite)
                        etSalterCAT.setText(okunan.salterCAT)
                        etSalterSTYLE.setText(okunan.salterSTYLE)
                        etSalterDemeraj.setText(okunan.salterDemeraj)
                        etSalterDegTarihi.setText(okunan.salterDegTarihi)
                        etSalterMCCYeri.setText(okunan.salterMccYeri)
                        etCekmeceDegTarihi.setText(okunan.cekmeceDegTarihi)
                    }
                }
            })

        ref.child("Surucu")
            .child(gelenMotorTag)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    val okunanSurucu = p0.getValue(SurucuModel::class.java)
                    if(okunanSurucu != null){

                        if(okunanSurucu.surucuIsim == "Kontaktör"){
                            etSalterKontaktorBoyut.setText(okunanSurucu.surucuBoyut)
                            etSalterKontaktorDIPSivic.setText(okunanSurucu.surucuDIPSivic)
                            etSalterSurucuModel.setText(okunanSurucu.surucuModel)
                            etSalterSurucuDegTarihi.setText(okunanSurucu.surucuDegTarihi)
                        }else {
                            etSalterSurucuModel.setText(okunanSurucu.surucuModel)
                            etSalterSurucuDegTarihi.setText(okunanSurucu.surucuDegTarihi)
                        }
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

        val data = FCMModel.Data("$motorTag TAG Nolu Çekmece","Düzenlendi","motor",kullaniciIsmi!!,motorTag)

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
}