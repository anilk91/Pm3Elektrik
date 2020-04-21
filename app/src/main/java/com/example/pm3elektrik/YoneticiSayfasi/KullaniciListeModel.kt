package com.example.pm3elektrik.YoneticiSayfasi

class KullaniciListeModel {

    var isim : String = ""
    var sicilNo : String = ""
    var kullaniciSifre : String = ""
    var motorYetki : String = ""
    var cekmeceYetki : String = ""
    var driveYetki : String = ""
    var ambarYetki : String = ""
    var telefonYetki : String = ""

    constructor(isim : String, sicilNo : String, kullaniciSifre : String, motorYetki : String, cekmeceYetki : String, driveYetki : String, ambarYetki : String, telefonYetki : String){

        this.isim = isim
        this.sicilNo = sicilNo
        this.kullaniciSifre = kullaniciSifre
        this.motorYetki = motorYetki
        this.cekmeceYetki = cekmeceYetki
        this.driveYetki = driveYetki
        this.ambarYetki = ambarYetki
        this.telefonYetki = telefonYetki
    }

    constructor()
}