package com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoEtiketDuzenle

import android.content.pm.PackageManager
import android.net.Uri
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

import com.example.pm3elektrik.R
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso

class TrafoGosterDuzenle : Fragment(){

    var trafoIsim: String? = null
    var izinlerVerildi = false
    var gelenResimUri : Uri? = null
    var photoView : PhotoView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_trafo_goster_duzenle, container, false)

        val edit = view.findViewById<ImageView>(R.id.imgTrafoEtiketDuzenle)
        val textIsim = view.findViewById<TextView>(R.id.tvTrafoEtiketIsim)


        val bundle: Bundle? = arguments
        trafoIsim = bundle?.getString("rvGelenTrafoIsim")
        Log.e("trafoIsim","$trafoIsim")

        textIsim.setText(trafoIsim)

        edit.setOnClickListener {

            if (izinlerVerildi){
                val dialogFragment = TrafoDuzenleDegistirDialog()
                dialogFragment.show(fragmentManager!!,"trafo_duzenle_dialog_fr")
            }else{
                galeriIzniniVer(view)
            }

        }


        return view
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

    fun gelenResimUri(resim: Uri?) {

        gelenResimUri = resim

        photoView = view?.findViewById<PhotoView>(R.id.imageView4)
        Log.e("gelenresim","$resim")

        Picasso.get().load(resim).into(photoView)



    }


}
