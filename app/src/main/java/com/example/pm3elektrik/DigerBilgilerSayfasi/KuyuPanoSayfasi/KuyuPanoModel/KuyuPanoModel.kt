package com.example.pm3elektrik.DigerBilgilerSayfasi.KuyuPanoSayfasi.KuyuPanoModel

class KuyuPanoModel {

    var kuyuIsim = ""
    var kuyuAdres = ""
    var kuyuSurucu = ""
    var kuyuDegTarihi = ""

    constructor(kuyuIsim: String, kuyuAdres: String, kuyuSurucu: String, kuyuDegTarihi: String) {
        this.kuyuIsim = kuyuIsim
        this.kuyuAdres = kuyuAdres
        this.kuyuSurucu = kuyuSurucu
        this.kuyuDegTarihi = kuyuDegTarihi
    }

    constructor()
}