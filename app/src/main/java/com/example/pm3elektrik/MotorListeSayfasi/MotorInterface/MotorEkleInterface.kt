package com.example.pm3elektrik.MotorListeSayfasi.MotorInterface

import android.content.Context
interface MotorEkleInterface {

    fun motorEkledenGelen(motorTag: String, motorMCCYeri: String, motorGucKW: Double, motorDevir: String, mContext : Context)
}