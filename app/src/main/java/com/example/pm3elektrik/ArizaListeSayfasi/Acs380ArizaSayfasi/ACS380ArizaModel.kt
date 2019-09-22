package com.example.pm3elektrik.ArizaListeSayfasi.Acs380ArizaSayfasi

class ACS380ArizaModel {

    var arizaKodu = ""
    var tanim = ""
    var tanimDetay = ""
    var aciklama1 = ""
    var aciklama2 = ""
    var aciklama3 = ""
    var aciklama4 = ""
    var aciklama5 = ""


    constructor()

    // 1 Açıklamalı Model-----------------------------
    constructor(arizaKodu: String, tanim: String, tanimDetay: String, aciklama1: String) {
        this.arizaKodu = arizaKodu
        this.tanim = tanim
        this.tanimDetay = tanimDetay
        this.aciklama1 = aciklama1
    }
    //-----------------------------------------------

    // 2 Açıklamalı Model---------------------------
    constructor(
        arizaKodu: String,
        tanim: String,
        tanimDetay: String,
        aciklama1: String,
        aciklama2: String
    ) {
        this.arizaKodu = arizaKodu
        this.tanim = tanim
        this.tanimDetay = tanimDetay
        this.aciklama1 = aciklama1
        this.aciklama2 = aciklama2
    }
    //---------------------------------------------



    // 3 Açıklamalı Model--------------------------
    constructor(
        arizaKodu: String,
        tanim: String,
        tanimDetay: String,
        aciklama1: String,
        aciklama2: String,
        aciklama3: String
    ) {
        this.arizaKodu = arizaKodu
        this.tanim = tanim
        this.tanimDetay = tanimDetay
        this.aciklama1 = aciklama1
        this.aciklama2 = aciklama2
        this.aciklama3 = aciklama3
    }
    //--------------------------------------------


    // 4 Açıklamalı Model-------------------------
    constructor(
        arizaKodu: String,
        tanim: String,
        tanimDetay: String,
        aciklama1: String,
        aciklama2: String,
        aciklama3: String,
        aciklama4: String
    ) {
        this.arizaKodu = arizaKodu
        this.tanim = tanim
        this.tanimDetay = tanimDetay
        this.aciklama1 = aciklama1
        this.aciklama2 = aciklama2
        this.aciklama3 = aciklama3
        this.aciklama4 = aciklama4
    }
    //--------------------------------------


    // 5 Açıklamalı Model------------------
    constructor(
        arizaKodu: String,
        tanim: String,
        tanimDetay: String,
        aciklama1: String,
        aciklama2: String,
        aciklama3: String,
        aciklama4: String,
        aciklama5: String
    ) {
        this.arizaKodu = arizaKodu
        this.tanim = tanim
        this.tanimDetay = tanimDetay
        this.aciklama1 = aciklama1
        this.aciklama2 = aciklama2
        this.aciklama3 = aciklama3
        this.aciklama4 = aciklama4
        this.aciklama5 = aciklama5
    }
    //------------------------------------
}