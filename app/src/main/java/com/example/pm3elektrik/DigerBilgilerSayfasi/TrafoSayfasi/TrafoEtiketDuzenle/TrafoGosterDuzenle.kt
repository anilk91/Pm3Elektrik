package com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoEtiketDuzenle

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoDuzenleDegistirDialogFR.TrafoDuzenleDegistirDialog
import com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoModel.TrafoModel

import com.example.pm3elektrik.R
import com.github.chrisbanes.photoview.PhotoView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class TrafoGosterDuzenle : Fragment(){

    private var trafoIsim: String? = null
    private var trafoResim: String? = null
    private var izinlerVerildi = false
    var photoView : PhotoView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_trafo_goster_duzenle, container, false)

        val edit = view.findViewById<ImageView>(R.id.imgTrafoEtiketDuzenle)
        val textIsim = view.findViewById<TextView>(R.id.tvTrafoEtiketIsim)

        photoView = view?.findViewById<PhotoView>(R.id.photoView)

        val bundle: Bundle? = arguments
        trafoIsim = bundle?.getString("rvGelenTrafoIsim")
        textIsim.setText(trafoIsim)

        val bundleResimDialog: Bundle? = arguments
        trafoResim = bundleResimDialog?.getString("trafoDialogGelenResim")
        Picasso.get().load(trafoResim).into(photoView)

        if (trafoIsim != null){
            val isimBoslukYok = trafoIsim!!.replace("\\s".toRegex(), "")

            firebaseDataBaseOku(view,isimBoslukYok)
        }
        edit.setOnClickListener {

            if (izinlerVerildi){

                val bundleDialogaGonder : Bundle? = Bundle()
                bundleDialogaGonder?.putString("dialogTrafoDuzenleyeIsimGonder",trafoIsim)
                val fragment = TrafoDuzenleDegistirDialog()
                fragment.arguments = bundleDialogaGonder
                fragment.show(fragmentManager!!,"trafo_duzenle_dialog_fr")

            }else{
                galeriIzniniVer(view)
            }

        }


        return view
    }

    private fun firebaseDataBaseOku(view: View, trafoGelenIsim: String) {

        val textIsim = view.findViewById<TextView>(R.id.tvTrafoEtiketIsim)
        val trafoDegTarihi = view.findViewById<TextView>(R.id.tvTrafoEtiketDegTarihi)
        val not = view.findViewById<TextView>(R.id.tvTrafoEtiketNot)
        val photoViewFB = view.findViewById<PhotoView>(R.id.photoView)

        FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Trafo").child(trafoGelenIsim)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    val gelen = p0.getValue(TrafoModel::class.java)

                    if (gelen != null){
                        if (gelen.trafoDegTarihi.isNullOrEmpty()){ trafoDegTarihi.setText("Bilgi Yok") }
                        else{ trafoDegTarihi.setText(gelen.trafoDegTarihi) }

                        if (gelen.trafoNot.isNullOrEmpty()){ not.setText("Bilgi Yok") }
                        else { not.setText(gelen.trafoNot) }

                        textIsim.setText(gelen.trafoIsim)
                        Picasso.get().load(gelen.trafoResimURL).into(photoViewFB)
                    }
                }
            })
    }
    private fun galeriIzniniVer(mView: View) {

        val izinler = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (ContextCompat.checkSelfPermission(mView.context,izinler[0]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mView.context,izinler[1]) == PackageManager.PERMISSION_GRANTED){

            izinlerVerildi = true
        }else {
            ActivityCompat.requestPermissions(activity!!,izinler,150)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == 150){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){

                val dialogFragment = TrafoDuzenleDegistirDialog()
                dialogFragment.show(fragmentManager!!,"trafo_duzenle_dialog_fr")
            }
        }else {

            Toast.makeText(view?.context,"Tüm İzinleri Vermeniz Gerekiyor",Toast.LENGTH_LONG).show()
        }
    }


}
