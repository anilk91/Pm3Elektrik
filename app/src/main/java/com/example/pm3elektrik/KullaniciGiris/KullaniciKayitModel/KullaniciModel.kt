package com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel

class KullaniciModel {

    var isim = ""
    var sicilNo = 0
    var kullaniciToken = ""

    constructor(isim: String, sicilNo: Int, bildirimToken: String) {
        this.isim = isim
        this.sicilNo = sicilNo
        this.kullaniciToken = bildirimToken
    }

    constructor()
}