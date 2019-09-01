package com.example.pm3elektrik.ArizaListeSayfasi.Acs140ArizaSayfasi


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
import kotlinx.android.synthetic.main.fragment_acs140.view.*


class Acs140Ariza : Fragment() {

    var arizaListe = ArrayList<ACS140ArizaModel>()
    lateinit var myAdapter : RVAcs140

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_acs140, container, false)

        gelenArizalar()


        val arizaAra = view.findViewById<EditText>(R.id.etACS140ArizaAra)
        arizaAra.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {

                if (p0 != null) {

                    val gelenVeri = p0.toString().toUpperCase()
                    val arananlar = ArrayList<ACS140ArizaModel>()

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


        myAdapter = RVAcs140(arizaListe,view.context)
        view?.rvACS140ArizaListe?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(view.context,RecyclerView.VERTICAL,false)
        view?.rvACS140ArizaListe?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()

        return view
    }


    private fun gelenArizalar() {

        arizaListe.add(ACS140ArizaModel("FL1","Overcurrent(Aşırı Akım)","-Mekanik Problem Olabilir","-Kalkış ve/veya Duruş Zamanı Çok Kısa","-Besleme Bozuk"))
        arizaListe.add(ACS140ArizaModel("FL2","DC Overvoltage(DC Aşırı Voltaj)","-Giriş Voltajı Çok Yüksek","-Duruş Zamanı Çok Kısa"))
        arizaListe.add(ACS140ArizaModel("FL3","ACS140 Overtemperature(ACS140 Aşırı Sıcak)","-Ortam Sıcaklığı Çok Yüksek","-Aşırı Yüke Dayalı Ciddi Zorlama"))
        arizaListe.add(ACS140ArizaModel("FL4","Fault Current(Kısa Devre)","-Toprak Hattına Kaçak Akım(Ünite Gövdesinde 200V var.(Tek Fazda Kısa Devre)","-Kısa Devre","-Besleme Bozuk"))
        arizaListe.add(ACS140ArizaModel("FL5","Output Overload(Çıkış Aşırı Yükü)"))
        arizaListe.add(ACS140ArizaModel("FL6","DC Undervoltage(DC Düşük Voltaj)"))
        arizaListe.add(ACS140ArizaModel("FL7","Analog Giriş 1 Hatalı", "-Analog Giriş 1 Değeri AL1(1301)'den düşük. Ayrıca 3001 nolu Parametreden (AL MIN FUNCTION and 3013 AL1 FAULT LIMIT) Bakınız."))
        arizaListe.add(ACS140ArizaModel("FL8","Analog Giriş 2 Hatalı", "-Analog Giriş 2 Değeri AL2(1304)'den düşük. Ayrıca 3001 nolu Parametreden (AL MIN FUNCTION and 3014 AL2 FAULT LIMIT) Bakınız."))
        arizaListe.add(ACS140ArizaModel("FL9","Motor Aşırı Sıcak", "Ayrıca 3004-3008 Arasındaki Parametrelere Bakınız."))
        arizaListe.add(ACS140ArizaModel("FL10","Kontrol Paneli Hatası. Start/Stop/Dir veya Referans Değeri Panele Geldikten Sonra Panel Haberleşmesi Koptu", "-Ayrıca 3002(PANEL LOSS and APPENDIX) Nolu Parametreye Bakınız.","NOT:Eğer güç kesilirken FL10 hatası aktif ise, ACS140 tekrar açıldığında uzaktan kontrol(REM) ile başlayacaktır."))
        arizaListe.add(ACS140ArizaModel("FL11","Parametreler Uyuşmuyor. Olası Hata Durumları","-MINUMUM AL1 > MAXIMUM AL1 (1301 ve 1302 Nolu Parametreler)","-MINUMUM AL2 > MAXIMUM AL2 (1304 ve 1305 Nolu Parametreler)","-MINUMUM FREQ > MAXIMUM FREQ (2007 ve 2008 Nolu Parametreler)"))
        arizaListe.add(ACS140ArizaModel("FL12","Motor Stall(Motor Sıkışık)","-Ayrıca 3009 Nolu (STALL FUNCTION) Parametrelerine Bakınız."))
        arizaListe.add(ACS140ArizaModel("FL13","Serial Communication Loss(Seri Haberleşme Kaybı)"))
        arizaListe.add(ACS140ArizaModel("FL14","Harici Hata Aktif","-3003 Nolu (EXTERNAL FAULT) Parametresine Bakınız."))
        arizaListe.add(ACS140ArizaModel("FL15","Toprak Hattına Kaçak Akım","-Ünite Gövdesinde 400V Var","-Üç Fazda Kısa Devre"))
        arizaListe.add(ACS140ArizaModel("FL16","DC Barada Çok Büyük Dalgalanma","-Beslemeyi Kontrol Ediniz"))
        arizaListe.add(ACS140ArizaModel("FL17","Analog Input Out Of Range(Analog Giriş Menzil Dışı)","-AL Kısmını Kontrol Et"))
        arizaListe.add(ACS140ArizaModel("FL18..FL22","Hardware Error(Donanım Hatası)","-Contact Supplier(Satıcı ile İletişime Geçiniz)"))
        arizaListe.add(ACS140ArizaModel("HATA","Full Display Blinking(Ekranın Tümü Yanıp Sönüyor), Serial Link Failure(Seri Link Hatası)","-ACS140 ve Kontrol Paneli Arasında Kötü Haberleşme","-Seri Haberleşme Parametreleri(GROUP 52) Değiştirilmiş","-Kontrol Panelini Kontrollü Bir Şekilde Çıkartıp Enerjiyi Kesin ve Daha Sonra Paneli Takıp Tekrar Enerjiyi Açınız."))
        arizaListe.add(ACS140ArizaModel("NOT","FL4, FL16, FL18-22 hataları ile karşılaşıldığında konvertör enerjisini tamamen kesip tekrar veriniz. Diğer tüm hatalarda panel üzerinden RESET butonuna(START/STOP Butonu) basmanız yeterlidir. Ayrıca 1604 parametresine bakınız."))
    }


}
