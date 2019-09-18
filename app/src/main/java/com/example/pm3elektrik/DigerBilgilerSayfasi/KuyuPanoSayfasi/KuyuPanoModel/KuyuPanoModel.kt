package com.example.pm3elektrik.DigerBilgilerSayfasi.KuyuPanoSayfasi.KuyuPanoModel

class KuyuPanoModel {

    var kuyuIsim = ""
    var kuyuAdres = ""
    var kuyuSurucu = ""
    var kuyuDegTarihi = ""
    var kuyuAnaBesleme = ""

    constructor(kuyuIsim: String, kuyuAdres: String, kuyuSurucu: String, kuyuDegTarihi: String, kuyuAnaBesleme : String) {
        this.kuyuIsim = kuyuIsim
        this.kuyuAdres = kuyuAdres
        this.kuyuSurucu = kuyuSurucu
        this.kuyuDegTarihi = kuyuDegTarihi
        this.kuyuAnaBesleme = kuyuAnaBesleme
    }

    constructor()
}