package com.example.pm3elektrik.YoneticiSayfasi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.rv_kullanici_liste.view.*
import kotlin.time.milliseconds

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
        var cardArti = tumLayout.imgYoneticiPanelKucultme as ImageView
        var silme = tumLayout.imgYoneticiKullaniciSilme as ImageView
        var onayla = tumLayout.btnYoneticiKullaniciOnayla as Button
        var motorYetki = tumLayout.checkBoxYoneticiMotor as CheckBox
        var cekmeceYetki = tumLayout.checkBoxYoneticiCekmece as CheckBox
        var driveYetki = tumLayout.checkBoxYoneticiDrive as CheckBox
        var ambarYetki = tumLayout.checkBoxYoneticiAmbar as CheckBox
        var telefonYetki = tumLayout.checkBoxYoneticiTelefon as CheckBox


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

        }
    }

}
