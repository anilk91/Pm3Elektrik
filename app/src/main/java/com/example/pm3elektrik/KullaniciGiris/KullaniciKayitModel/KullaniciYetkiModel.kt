package com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel

class KullaniciYetkiModel {

    var motorYetki = false
    var cekmeceYetki = false
    var driveUniteYetki = false
    var telefonYetki = false
    var ambarKayitYetki = false

    constructor(motorYetki: Boolean, cekmeceYetki: Boolean, driveUniteYetki: Boolean, telefonYetki: Boolean, ambarKayitYetki: Boolean) {

        this.motorYetki = motorYetki
        this.cekmeceYetki = cekmeceYetki
        this.driveUniteYetki = driveUniteYetki
        this.telefonYetki = telefonYetki
        this.ambarKayitYetki = ambarKayitYetki
    }

    constructor()
}