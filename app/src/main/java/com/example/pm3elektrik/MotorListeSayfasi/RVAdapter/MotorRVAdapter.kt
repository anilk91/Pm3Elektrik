package com.example.pm3elektrik.MotorListeSayfasi.RVAdapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.MotorListeSayfasi.CekmecesiSalterOlanSayfa.CekmecesiSalterOlanEtiket
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUnite
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorveSalterEtiketleri.MotorVeSalterEtiket
import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.motor_rv_adapter.view.*
import kotlinx.android.synthetic.main.motor_rv_adapter.view.tvMotorDevir
import kotlinx.android.synthetic.main.motor_rv_adapter.view.tvMotorEtiketTag
import kotlinx.android.synthetic.main.motor_rv_adapter.view.tvMotorGuc
import kotlinx.android.synthetic.main.motor_rv_adapter.view.tvMotorMCCYeri

class MotorRVAdapter(var motorListe: ArrayList<MotorModel>, var mContext: Context, var activity: FragmentActivity?):RecyclerView.Adapter<MotorRVAdapter.MyData>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_motor_liste_adapter,parent,false)
        return MyData(view)
    }

    override fun getItemCount(): Int {

        return motorListe.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {
    holder.setData(motorListe.get(position),position)
    }

    inner class MyData(itemView : View):RecyclerView.ViewHolder(itemView) {

        var tumLayout = itemView as ConstraintLayout
        var motorTag = tumLayout.tvMotorEtiketTag
        var mCCYeri = tumLayout.tvMotorMCCYeri
        var motorGuc = tumLayout.tvMotorGuc
        var motorDevir = tumLayout.tvMotorDevir
        var devirYazisi = tumLayout.tvDevirYazisi
        var gucYazisi = tumLayout.tvGucYazisi
        var motorBilgi = tumLayout.imgBilgiButton as ImageView
        var motorDelete = tumLayout.imgDeleteButton as ImageView


        fun setData(motorListesi: MotorModel, position: Int) {

            //-----DİĞER MOTORDAN GELEN BİLGİLER-----------------------------------
            if (motorListesi.motorGelenVeri == "motorEkle"){
                if (motorListesi.motorDevir.isNullOrBlank()){
                    devirYazisi.setText("Devir :")
                    motorDevir.setText("Bilgi Yok")
                }else{
                    devirYazisi.setText("Devir :")
                    motorDevir.setText("${motorListesi.motorDevir} D/d")
                }
                if (motorListesi.motorGucKW == 0.0){
                    gucYazisi.setText("Güç :")
                    motorGuc.setText("Bilgi Yok")
                }else{
                    gucYazisi.setText("Güç :")
                    motorGuc.setText("${motorListesi.motorGucKW} KW")
                }
                motorTag.setText(motorListesi.motorTag)
                mCCYeri.setText(motorListesi.motorMCCYeri)
            }

            //-----DRİVE MOTORDAN GELEN BİLGİLER-----------------------------------
            if (motorListesi.motorGelenVeri == "driveMotorEkle"){
                if (motorListesi.motorDevir.isNullOrBlank()){
                    devirYazisi.setText("Devir :")
                    motorDevir.setText("Bilgi Yok")
                }else{
                    devirYazisi.setText("Devir :")
                    motorDevir.setText("${motorListesi.motorDevir} D/d")
                }
                if (motorListesi.motorGucKW == 0.0 ){
                    gucYazisi.setText("Güç :")
                    motorGuc.setText("Bilgi Yok")
                }else{
                    gucYazisi.setText("Güç :")
                    motorGuc.setText("${motorListesi.motorGucKW} KW")
                }
                motorTag.setText(motorListesi.motorTag)
                mCCYeri.setText(motorListesi.motorMCCYeri)
            }


            //-----ÇEKMECEDEN GELEN BİLGİLER-----------------------------------
            if (motorListesi.motorGelenVeri == "cekmeceEkle"){
                motorTag.setText(motorListesi.motorTag)
                mCCYeri.setText(motorListesi.motorMCCYeri)
                if(motorListesi.cekmeceKapasite.isNullOrBlank()){
                    devirYazisi.setText("Kapasite :")
                    motorDevir.setText("Bilgi Yok")
                }else{
                    devirYazisi.setText("Kapasite :")
                    motorDevir.setText("${motorListesi.cekmeceKapasite} A")
                }

                if(motorListesi.cekmeceMarka.isNullOrBlank()){
                    gucYazisi.setText("Şalter :")
                    motorGuc.setText("Bilgi Yok")
                }else{
                    gucYazisi.setText("Şalter :")
                    motorGuc.setText("${motorListesi.cekmeceMarka}\nModel - ${motorListesi.cekmeceModel}")
                }
            }

            motorBilgi.setOnClickListener {

                if (motorListesi.motorGelenVeri == "driveMotorEkle"){

                    val bundle : Bundle? =Bundle()
                    bundle?.putString("rvGelenMotorTag",motorListe[position].motorTag)
                    val fragment = DriveUnite()
                    fragment.arguments = bundle
                    val transaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.replace(R.id.containerMotorListe,fragment,"rv_fragment")?.commit()

                }

                if (motorListesi.motorGelenVeri == "motorEkle"){

                    val bundle : Bundle? =Bundle()
                    bundle?.putString("rvGelenMotorTag",motorListe[position].motorTag)
                    val fragment = MotorVeSalterEtiket()
                    fragment.arguments = bundle
                    val transaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.replace(R.id.containerMotorListe,fragment,"rv_fragment")?.commit()
                }

                if (motorListesi.motorGelenVeri == "cekmeceEkle"){

                    val bundle : Bundle? =Bundle()
                    bundle?.putString("rvGelenTag",motorListe[position].cekmeceUid)
                    val fragment = CekmecesiSalterOlanEtiket()
                    fragment.arguments = bundle
                    val transaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.replace(R.id.containerMotorListe,fragment,"rv_fragment")?.commit()
                }
            }
            motorDelete.setOnClickListener {

                //AlertDialog Penceresi------------------------------
                val builder = AlertDialog.Builder(mContext)
                builder.setTitle("Seçimi Sil?")
                builder.setMessage("${motorListesi.motorTag} Etiketine Ait Tüm Bilgileri Silmek İstiyor Musunuz?")

                builder.setPositiveButton("EVET", object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {

                        //Çekmece ve Motor Bilgilerini Sil----------------------------
                        FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
                            .child("Motor")
                            .orderByChild("motorTag")
                            .equalTo(motorListesi.motorTag)
                            .addValueEventListener(object : ValueEventListener{
                                override fun onCancelled(p0: DatabaseError) {}
                                override fun onDataChange(p0: DataSnapshot) {

                                    for (gelen in p0.children){
                                        gelen.ref.removeValue()

                                    }
                                }
                            })
                        //Şalter Bilgilerini Sil--------------------------------
                        FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
                            .child("Salter")
                            .child(motorListe[position].motorTag)
                            .removeValue()

                        //Sürücü Bilgilerini Sil---------------------------------
                        FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
                            .child("Surucu")
                            .child(motorListe[position].motorTag)
                            .removeValue()

                        motorListe.removeAt(position)
                        notifyDataSetChanged()
//                        notifyItemRemoved(position)
//                        notifyItemRangeChanged(position,motorListe.size)

                        Toast.makeText(mContext,"${motorListesi.motorTag} Silindi!",Toast.LENGTH_SHORT).show()
                    }
            })
                builder.setNegativeButton("HAYIR", object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Toast.makeText(mContext,"Seçim Silinmedi!",Toast.LENGTH_SHORT).show()
                    }
                })

                val dialog : AlertDialog =builder.create()
                dialog.show()


            }


        }
    }

    fun gelenMotorTagiFiltrele(gelenTag : ArrayList<MotorModel>){

        motorListe = ArrayList<MotorModel>()
        motorListe.addAll(gelenTag)
        notifyDataSetChanged()
    }
}