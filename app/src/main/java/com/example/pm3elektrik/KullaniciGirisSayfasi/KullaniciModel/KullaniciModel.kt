package com.example.pm3elektrik.KullaniciGirisSayfasi.KullaniciModel

class KullaniciModel {

    var isim = ""
    var sicilNo = 0
    var mailAdres = ""
    var kullaniciUid = ""

    constructor(isim: String, sicilNo: Int, mailAdres: String, kullaniciUid: String) {

        this.isim = isim
        this.sicilNo = sicilNo
        this.mailAdres = mailAdres
        this.kullaniciUid = kullaniciUid
    }

    constructor()
}