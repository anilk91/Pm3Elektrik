package com.example.pm3elektrik.YoneticiSayfasi

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.rv_kullanici_liste.view.*

class RVKullaniciListe (var kullaniciListe : ArrayList<KullaniciModel>, var mContext : Context) : RecyclerView.Adapter<RVKullaniciListe.MyData>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_kullanici_liste,parent,false)
        return MyData(view)
    }
    override fun getItemCount(): Int {
       return kullaniciListe.size
    }
    override fun onBindViewHolder(holder: MyData, position: Int) {
        holder.setData(kullaniciListe.get(position),position)
    }

    inner class MyData(itemView : View): RecyclerView.ViewHolder(itemView) {

        var tumLayout = itemView as ConstraintLayout
        var isim = tumLayout.tvYoneticiKullaniciIsmi
        var sicilNo = tumLayout.tvYoneticiKullaniciSicilNo
        var sifre = tumLayout.tvYoneticiKullaniciSifre
        var gizliSicilNo = tumLayout.tvYoneticiGizliSicilNo
        var cardArti = tumLayout.imgYoneticiPanelKucultme as ImageView
        var silme = tumLayout.imgYoneticiKullaniciSilme as ImageView
        var onayla = tumLayout.btnYoneticiKullaniciOnayla as Button
        var motorYetki = tumLayout.checkBoxYoneticiMotor as CheckBox
        var cekmeceYetki = tumLayout.checkBoxYoneticiCekmece as CheckBox
        var driveYetki = tumLayout.checkBoxYoneticiDrive as CheckBox
        var ambarYetki = tumLayout.checkBoxYoneticiAmbar as CheckBox
        var telefonYetki = tumLayout.checkBoxYoneticiTelefon as CheckBox
        var kullaniciPanel = tumLayout.cardViewYoneticiKullanici as CardView


        fun setData(kullaniciListe : KullaniciModel, position: Int){

            if (kullaniciListe.isim.isNotEmpty()){
                isim.setText(kullaniciListe.isim)
            }else{
                isim.setText("Kayıt Bulunamadı")
            }
            if (kullaniciListe.sicilNo != 0){
                sicilNo.setText(kullaniciListe.sicilNo.toString())
            }
            if (kullaniciListe.sifre.isNotEmpty()){
                sifre.setText(kullaniciListe.sifre)
            }else{
                sifre.setText("Kayıt Bulunamadı")
            }

            //YETKİ KONTROLÜ YAPILIP CHECKBOX ONAY KUTULARINA İŞLENİYOR
            if (kullaniciListe.motorYetki == "var"){
                motorYetki.isChecked = true
            }
            if (kullaniciListe.cekmeceYetki == "var"){
                cekmeceYetki.isChecked = true
            }
            if (kullaniciListe.driveUniteYetki == "var"){
                driveYetki.isChecked = true
            }
            if (kullaniciListe.ambarKayitYetki == "var"){
                ambarYetki.isChecked = true
            }
            if (kullaniciListe.telefonYetki == "var"){
                telefonYetki.isChecked = true
            }

            //ARTI TUŞUNA BASILDIĞINDA KULLANICI BİLGİLERİNİ GENİŞLETME VE KÜÇÜLTME

            cardArti.setOnClickListener {
                if (kullaniciPanel.visibility == View.GONE){
                    kullaniciPanel.visibility = View.VISIBLE
                    gizliSicilNo.setText(kullaniciListe.sicilNo.toString())
                    gizliSicilNo.visibility = View.GONE

                }else{
                    kullaniciPanel.visibility = View.GONE
                    gizliSicilNo.setText(kullaniciListe.sicilNo.toString())
                    gizliSicilNo.visibility = View.VISIBLE
                }
            }

            //SEÇİLİ KULLANICI BİLGİLERİNİ TAMAMEN SİLME

            silme.setOnClickListener {

                            val builder = AlertDialog.Builder(mContext)
                            builder.setTitle("Seçimi Sil?")
                            builder.setMessage("${kullaniciListe.sicilNo} Kullanıcı Bilgilerini Silmek İstiyor Musunuz?")

                            builder.setPositiveButton("EVET", object : DialogInterface.OnClickListener{
                                override fun onClick(p0: DialogInterface?, p1: Int) {

                                    firebasedenKullaniciBilgisiOkuveSil(kullaniciListe)

                                }

                            })

                            builder.setNegativeButton("HAYIR", object : DialogInterface.OnClickListener {
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    Toast.makeText(mContext, "Seçim Silinmedi!", Toast.LENGTH_SHORT).show()
                                }
                            })

                            val dialog: AlertDialog = builder.create()
                            dialog.show()


            }

        }

        private fun firebasedenKullaniciBilgisiOkuveSil(kullaniciListe: KullaniciModel) {


            FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar")
                .orderByKey()
                .equalTo("${kullaniciListe.sicilNo}")
                .addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(p0: DataSnapshot) {
                        for (gelen in p0.children) {
                            gelen.ref.removeValue()
                        }
                    }

                })

        }
    }

}
