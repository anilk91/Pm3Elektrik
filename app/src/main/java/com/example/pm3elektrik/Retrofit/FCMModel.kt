package com.example.pm3elektrik.Retrofit

import com.google.gson.annotations.SerializedName

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

        constructor(baslik: String, icerik: String, bildirim_turu: String) {
            this.baslik = baslik
            this.icerik = icerik
            this.bildirim_turu = bildirim_turu
        }
    }
}