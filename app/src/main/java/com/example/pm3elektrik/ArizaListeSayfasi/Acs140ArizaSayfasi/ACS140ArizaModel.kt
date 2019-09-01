package com.example.pm3elektrik.ArizaListeSayfasi.Acs140ArizaSayfasi

class ACS140ArizaModel {

    var arizaKodu = ""
    var tanim = ""
    var madde1 = ""
    var madde2 = ""
    var madde3 = ""

    constructor(arizaKodu: String, tanim: String, madde1: String, madde2: String, madde3: String) {
        this.arizaKodu = arizaKodu
        this.tanim = tanim
        this.madde1 = madde1
        this.madde2 = madde2
        this.madde3 = madde3
    }

    constructor(arizaKodu: String, tanim: String, madde1: String, madde2: String) {
        this.arizaKodu = arizaKodu
        this.tanim = tanim
        this.madde1 = madde1
        this.madde2 = madde2
    }

    constructor(arizaKodu: String, tanim: String, madde1: String) {
        this.arizaKodu = arizaKodu
        this.tanim = tanim
        this.madde1 = madde1
    }

    constructor(arizaKodu: String, tanim: String) {
        this.arizaKodu = arizaKodu
        this.tanim = tanim
    }

    constructor()


}