package com.example.pm3elektrik.Retrofit

import com.google.gson.annotations.SerializedName
import java.lang.reflect.Array

class FCMModel {

    @SerializedName("to")
    var to : String? = null
    @SerializedName("data")
    var data : Data? = null

    constructor(to:String , data: Data) {
        this.to = to
        this.data = data
    }


    class Data {
        @SerializedName("baslik")
        var baslik : String? = null
        @SerializedName("icerik")
        var icerik : String? = null
        @SerializedName("bildirim_turu")
        var bildirim_turu : String? = null
        @SerializedName("gonderen_isim")
        var gonderen_isim : String? = null

        constructor(baslik: String, icerik: String, bildirim_turu: String, gonderen_isim : String) {
            this.baslik = baslik
            this.icerik = icerik
            this.bildirim_turu = bildirim_turu
            this.gonderen_isim = gonderen_isim
        }
    }
}