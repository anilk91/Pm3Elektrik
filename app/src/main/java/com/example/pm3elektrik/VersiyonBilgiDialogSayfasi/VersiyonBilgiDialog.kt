package com.example.pm3elektrik.VersiyonBilgiDialogSayfasi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.activity_ana_sayfa.*
import kotlinx.android.synthetic.main.fragment_versiyon_bilgi.view.*

class VersiyonBilgiDialog : DialogFragment() {

    var versiyonTanim = ArrayList<VersiyonBilgiModel>()
    lateinit var myAdapter : RVversiyonBilgi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_versiyon_bilgi, container, false)

        val btnKapat = view.findViewById<Button>(R.id.btnVersiyonSayfaKapat)
        val rvVersiyonListe = view.findViewById<RecyclerView>(R.id.rvVersiyonListesi)

        btnKapat.setOnClickListener {
            dismiss()
        }

        versiyonAciklamalari(view)

        return view
    }

    private fun versiyonAciklamalari(view: View) {

        versiyonTanim.add(VersiyonBilgiModel("-Motor, telefon, drive motor ve ünite, ambar kaydı listesine ekleme yapma, silme ve düzenleme işlemleri yetkisi olanlar tarafından oluşturulabilir."))
        versiyonTanim.add(VersiyonBilgiModel("-Motor ve sürücüsünün detaylı etiket bilgileri oluşturulup düzenlenebilir."))
        versiyonTanim.add(VersiyonBilgiModel("-Drive motorlarının ünite bakım bilgileri ve seri numaraları dinamik olarak eklenip düzenlenebilir."))
        versiyonTanim.add(VersiyonBilgiModel("-Motor, telefon, arıza kayıtları ve ambar kayıtları için hızlı arama eklendi."))
        versiyonTanim.add(VersiyonBilgiModel("-Arıza listeleri eklendi."))
        versiyonTanim.add(VersiyonBilgiModel("-Kontaktör trip tablosu eklendi."))
        versiyonTanim.add(VersiyonBilgiModel("-Kontaktör trip tablosunda motor akımına göre veya dip siviç ayarına göre bilgiler girilip daha hızlı bir şekilde akım bilgisi bulunabilecek şekilde güncelleme yapıldı."))
        versiyonTanim.add(VersiyonBilgiModel("-Pm3 sahasındaki kuyu panolarının bulunduğu yerler eklendi."))
        versiyonTanim.add(VersiyonBilgiModel("-Akım konvertörünün gerçekte ölçmesi gereken çıkış için hesaplama özelliği eklendi."))
        versiyonTanim.add(VersiyonBilgiModel("-Kumanda projeleri eklendi."))
        versiyonTanim.add(VersiyonBilgiModel("-Kayıt olan kullanıcı için hangi bölümlerde yetkisi olabileceğini gördüğü yetki sayfası oluşturuldu."))

        okunanVersiyonBilgileri(versiyonTanim,view)
    }

    private fun okunanVersiyonBilgileri(gelenVersiyon: ArrayList<VersiyonBilgiModel>, view: View){

        myAdapter = RVversiyonBilgi(gelenVersiyon)
        view.rvVersiyonListesi?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL,false)
        view.rvVersiyonListesi?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()
    }

}
