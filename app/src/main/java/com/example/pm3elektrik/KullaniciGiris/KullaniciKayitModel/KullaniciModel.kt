package com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel

class KullaniciModel {

    var isim :String = ""
    var sicilNo : Int = 0
    var sifre :String = ""
    var kullaniciToken :String = ""
    var motorYetki :String = "yok"
    var cekmeceYetki :String = "yok"
    var driveUniteYetki :String = "yok"
    var telefonYetki :String = "yok"
    var ambarKayitYetki :String = "yok"


    constructor(

        isim: String,
        sicilNo: Int,
        kullaniciToken: String,
        motorYetki: String,
        cekmeceYetki: String,
        driveUniteYetki: String,
        telefonYetki: String,
        ambarKayitYetki: String

    ) {

        this.isim = isim
        this.sicilNo = sicilNo
        this.kullaniciToken = kullaniciToken
        this.motorYetki = motorYetki
        this.cekmeceYetki = cekmeceYetki
        this.driveUniteYetki = driveUniteYetki
        this.telefonYetki = telefonYetki
        this.ambarKayitYetki = ambarKayitYetki

    }

    constructor(motorYetki: String,
                cekmeceYetki: String,
                driveUniteYetki: String,
                telefonYetki: String,
                ambarKayitYetki: String){

        this.motorYetki = motorYetki
        this.cekmeceYetki = cekmeceYetki
        this.driveUniteYetki = driveUniteYetki
        this.telefonYetki = telefonYetki
        this.ambarKayitYetki = ambarKayitYetki

    }

    constructor(

        isim: String,
        sicilNo: Int,
        sifre : String,
        kullaniciToken: String,
        motorYetki: String,
        cekmeceYetki: String,
        driveUniteYetki: String,
        telefonYetki: String,
        ambarKayitYetki: String

    ) {

        this.isim = isim
        this.sicilNo = sicilNo
        this.sifre = sifre
        this.kullaniciToken = kullaniciToken
        this.motorYetki = motorYetki
        this.cekmeceYetki = cekmeceYetki
        this.driveUniteYetki = driveUniteYetki
        this.telefonYetki = telefonYetki
        this.ambarKayitYetki = ambarKayitYetki

    }

    constructor(sifre : String){
        this.sifre = sifre
    }


    constructor()
}