package com.example.pm3elektrik.ArizaListeSayfasi.DanfossArizaSayfasi


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.fragment_danfoss_ariza_sayfasi.view.*


class DanfossAriza : Fragment() {

    val arizaListe = ArrayList<DanfossArizaModel>()
    lateinit var myAdapter : RVDanfoss

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_danfoss_ariza_sayfasi, container, false)
        arizalariEkle()

        val arizaAra = view.findViewById<EditText>(R.id.etDanfossArizaArama)

        arizaAra.addTextChangedListener(object : TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {

                if (p0 != null) {

                    val gelenVeri = p0.toString()
                    val arananlar = ArrayList<DanfossArizaModel>()

                    for (gelen in arizaListe) {

                        val bulunanArizaKodu = gelen.arizaKodu
                        if (bulunanArizaKodu.contains(gelenVeri)) {
                            arananlar.add(gelen)
                        }
                    }
                    if (myAdapter != null) {
                        myAdapter.gelenArizaKodunaGoreFiltrele(arananlar)
                    }
                }

            }



        })

        myAdapter = RVDanfoss(arizaListe,view.context)
        view?.rvDanfossArizaListesi?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL,false)
        view?.rvDanfossArizaListesi?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()


        return view
    }

    fun arizalariEkle(){

        arizaListe.add(DanfossArizaModel("2","Live Zero Error(Canlı Sıfır Hatası)"))
        arizaListe.add(DanfossArizaModel("4","Mains Phase Loss(Ana Faz Kaybı)"))
        arizaListe.add(DanfossArizaModel("5","Voltage Warning Low(DC Link High Voltage) Düşük Voltaj Uyarısı(DC Hattı Yüksek Voltaj)"))
        arizaListe.add(DanfossArizaModel("6","Voltage Warning Low(DC Link Low Voltage) Düşük Voltaj Uyarısı(DC Hattı Düşük Voltaj)"))
        arizaListe.add(DanfossArizaModel("7","Overvoltage(DC Link Overvoltage) Aşırı Voltaj(DC Hattı Aşırı Voltaj)"))
        arizaListe.add(DanfossArizaModel("8","Undervoltage(DC Link Undervoltage) Düşük Voltaj(DC Hattı Düşük Voltaj)"))
        arizaListe.add(DanfossArizaModel("9","Inverter Overload(Inverter Time) İnverter Aşırı Yük(İnverter Zamanı)"))
        arizaListe.add(DanfossArizaModel("10","Motor Overvoltage(Motor Time) Motor Aşırı Voltaj(Motor Zamanı)"))
        arizaListe.add(DanfossArizaModel("11","Motor Thermistor(Motor Sıcaklık)"))
        arizaListe.add(DanfossArizaModel("12","Current Limit(Akım Limiti)"))
        arizaListe.add(DanfossArizaModel("13","Overcurrent(Aşırı Akım)"))
        arizaListe.add(DanfossArizaModel("14","Earth Fault(Topraklama Hatası)"))
        arizaListe.add(DanfossArizaModel("15","Switch Mode Fault(Siviç Mod Hatası)"))
        arizaListe.add(DanfossArizaModel("16","Short Circuit(Kısa Devre)"))
        arizaListe.add(DanfossArizaModel("17","Serial Communication Timeout(STD Bus Timeout) Seri Haberleşme Zaman Aşımı(STD Bus Zaman Aşımı"))
        arizaListe.add(DanfossArizaModel("18","HPFB Bus Timeout(HPFB Bus Zaman Aşımı)"))
        arizaListe.add(DanfossArizaModel("33","Out of Frequency Range(Out Freq RNG/ROT Limit) Frekans Aralığı Dışında"))
        arizaListe.add(DanfossArizaModel("34","HPFB Communication Fault(Profibus Opt.) HPFB Haberleşme Hatası"))
        arizaListe.add(DanfossArizaModel("35","Inrush Fault(Ani Deşarj Hatası)"))
        arizaListe.add(DanfossArizaModel("36","Overtemperature(Aşırı Sıcaklık)"))
        arizaListe.add(DanfossArizaModel("37..45","Internal Fault(Dahili Hata)"))
        arizaListe.add(DanfossArizaModel("50","AMT Not Possible(AMT Geçersiz)"))
        arizaListe.add(DanfossArizaModel("51","AMT Fault re. Nameplate Data(AMT Type Data Fault) AMT Hatası"))
        arizaListe.add(DanfossArizaModel("54","AMT Wrong Motor(AMT Hatalı Motor"))
        arizaListe.add(DanfossArizaModel("55","AMT Timeout(AMT Zaman Aşımı"))

    }


}
