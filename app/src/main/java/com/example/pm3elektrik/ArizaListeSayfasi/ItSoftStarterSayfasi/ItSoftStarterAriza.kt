package com.example.pm3elektrik.ArizaListeSayfasi.ItSoftStarterSayfasi


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
import kotlinx.android.synthetic.main.fragment_it_soft_starter.view.*

class ItSoftStarterAriza : Fragment() {

    val arizaListe = ArrayList<ItSoftStarterArizaModel>()
    lateinit var myAdapter : RVItSoftStarter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_it_soft_starter, container, false)

        arizaListeyeEkle()
        val arizaAra = view.findViewById<EditText>(R.id.etItSoftStarterArizaAra)

        arizaAra.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {


                if (p0 != null) {

                    val gelenVeri = p0.toString()
                    val arananlar = ArrayList<ItSoftStarterArizaModel>()

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


        myAdapter = RVItSoftStarter(arizaListe,view.context)
        view?.rvItSoftStarterListe?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL,false)
        view?.rvItSoftStarterListe?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()


        return view
    }

    private fun arizaListeyeEkle() {
        arizaListe.add(ItSoftStarterArizaModel("11","Thermal Overload(Sıcaklık Çok Yüksek)","See page 42"))
        arizaListe.add(ItSoftStarterArizaModel("12","Motor Stall(Motor Sıkışık)","See page 42"))
        arizaListe.add(ItSoftStarterArizaModel("13","Motor Jam(Motor Sıkışık)","See page 42"))
        arizaListe.add(ItSoftStarterArizaModel("14","Phase Sequence ACB(Faz Sıralaması ACB Şeklinde)","See page 42"))
        arizaListe.add(ItSoftStarterArizaModel("15","Pole Over-Temperature(Faz-Kutup Aşırı Sıcak)","See page 42"))
        arizaListe.add(ItSoftStarterArizaModel("16","SCR Failed to Fire(SCR Ateşlemedi)","Extreme settings on lightly loaded motor. Loose connection or defective unit."))
        arizaListe.add(ItSoftStarterArizaModel("21","15V Power Supply Low(15V Güç Beslemesi Düşük)","Weak control power or defective unit."))
        arizaListe.add(ItSoftStarterArizaModel("22","Phase Loss(Faz Kaybı)","See page 42"))
        arizaListe.add(ItSoftStarterArizaModel("23","Bypass Dropout(Bypass Tamamlanamadı)","Weak control power or defective unit."))
        arizaListe.add(ItSoftStarterArizaModel("24","SCR/Contactor Overcurrent(SCR/Kontaktör Aşırı Akım)","Only if stall or jam disabled, load amps excessive"))
        arizaListe.add(ItSoftStarterArizaModel("25","Phase Unbalance(Faz Dengesiz)","See page 42"))
        arizaListe.add(ItSoftStarterArizaModel("26","Non-Volatile Memory Error(Kalıcı Hafıza Hatası)","Internal control board fault"))
        arizaListe.add(ItSoftStarterArizaModel("31","Zero Voltage Cross Failure(Sıfır Voltaj Çakışma Hatası)","Control power applied out of sequence or power factor correction capacitors on motor"))
        arizaListe.add(ItSoftStarterArizaModel("32","Shorted SCR, Phase Loss, Load Disconnect(SCR Kısa Devre, Faz Kaybı, Yük Bağlantısı Kesik-Yok)","See page 42"))
        arizaListe.add(ItSoftStarterArizaModel("33","Load Disconnect(Yük Bağlantısı Kesik-Yok)","Load current falls below 1/16 of FLA setting. Can be disabled by setting phase loss to disable"))
        arizaListe.add(ItSoftStarterArizaModel("34","SCR Instantaneous Overcurrent(SCR Anlık Aşırı Akım)","Current exceeded start ratings during the starting or stopping mode"))
        arizaListe.add(ItSoftStarterArizaModel("41","24V Power Supply Low(24V Güç Beslemesi Düşük)","Improper 24V DC supply or weak control power"))
        arizaListe.add(ItSoftStarterArizaModel("42","Timer System Fault(Zamanlayıcı Sistem Hatası)","Internal control board fault"))
        arizaListe.add(ItSoftStarterArizaModel("43","Watchdog Reset Occured(İzci Sıfırlandı)","External electrical noise or internal control board fault"))
        arizaListe.add(ItSoftStarterArizaModel("44","PLL(DSP)","Internal control board fault"))
        arizaListe.add(ItSoftStarterArizaModel("45","Illegal Adress(DSP)(İllegal Adres)","Internal control board fault"))


            }


}
