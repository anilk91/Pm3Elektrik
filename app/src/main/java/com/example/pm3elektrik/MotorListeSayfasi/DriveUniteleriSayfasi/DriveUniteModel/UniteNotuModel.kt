package com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteModel

class UniteNotuModel {

    var uniteNotu = ""
    var uniteNotuTarih = ""
    var sistemSaat = ""

    constructor(uniteNotu: String, uniteNotuTarih: String, sistemSaat : String) {
        this.uniteNotu = uniteNotu
        this.uniteNotuTarih = uniteNotuTarih
        this.sistemSaat = sistemSaat
    }

    constructor()
}