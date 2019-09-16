package com.example.pm3elektrik.MotorListeSayfasi.CekmecesiSalterOlanSayfa.CekmesiSalterOlanModel

class CekmeceSalterModel {

    var isim = ""
    var marka = ""
    var model = ""
    var cat = ""
    var kapasite = ""
    var demeraj = ""
    var mccYeri = ""
    var degTarihi = ""

    constructor(
        isim: String,
        marka: String,
        model: String,
        cat: String,
        kapasite: String,
        demeraj: String,
        mccYeri: String,
        degTarihi: String
    ) {
        this.isim = isim
        this.marka = marka
        this.model = model
        this.cat = cat
        this.kapasite = kapasite
        this.demeraj = demeraj
        this.mccYeri = mccYeri
        this.degTarihi = degTarihi
    }

    constructor()
}