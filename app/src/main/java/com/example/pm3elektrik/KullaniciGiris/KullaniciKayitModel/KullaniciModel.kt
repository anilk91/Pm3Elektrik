package com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel

class KullaniciModel {

    var isim = ""
    var sicilNo = 0
    var kullaniciToken = ""
    var motorYetki = false
    var cekmeceYetki = false
    var driveUniteYetki = false
    var telefonYetki = false
    var ambarKayitYetki = false

    constructor(
        isim: String,
        sicilNo: Int,
        kullaniciToken: String,
        motorYetki: Boolean,
        cekmeceYetki: Boolean,
        driveUniteYetki: Boolean,
        telefonYetki: Boolean,
        ambarKayitYetki: Boolean
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

    constructor()
}