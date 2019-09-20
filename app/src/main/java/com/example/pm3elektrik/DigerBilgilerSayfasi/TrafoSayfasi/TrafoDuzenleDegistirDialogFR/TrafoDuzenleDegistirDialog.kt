package com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoDuzenleDegistirDialogFR


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoEtiketDuzenle.TrafoGosterDuzenle

import com.example.pm3elektrik.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_ana_sayfa.*
import java.io.ByteArrayOutputStream

class TrafoDuzenleDegistirDialog : DialogFragment() {

    var galeridenGelenResimYolu : String? = null
    var gelenResimYoluURI : Uri? = null
    var trafoIsim : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_trafo_duzenle_degistir, container, false)

        val fotoYukle = view.findViewById<Button>(R.id.btnTrafoFotoYukle)
        val ekle = view.findViewById<Button>(R.id.btnTrafoBilgiEkle)
        val degTarihi = view.findViewById<EditText>(R.id.etTrafoDegTarihi)
        val not = view.findViewById<EditText>(R.id.etTrafoNot)

        val bundleTrafoIsim: Bundle? = arguments
        trafoIsim = bundleTrafoIsim?.getString("dialogTrafoDuzenleyeIsimGonder")
        Log.e("gelenTrafoIsim","$trafoIsim")

        fotoYukle.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, 100)
        }

        ekle.setOnClickListener {

            resimKompres(gelenResimYoluURI)
            dismiss()
        }

        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != Activity.RESULT_CANCELED) {
            if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {

                galeridenGelenResimYolu = data.data.toString()
                gelenResimYoluURI = data.data

                val bundle : Bundle? =Bundle()
                bundle?.putString("trafoDialogGelenResim",galeridenGelenResimYolu)
                val fragment = TrafoGosterDuzenle()
                fragment.arguments = bundle
                val transaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.containerTrafoEtiketDuzenle,fragment,"rv_fragment")?.commit()

            }
        }
    }

    inner class ArkaPlanResimKompres : AsyncTask<Uri, Double, ByteArray?>(){

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params : Uri?): ByteArray? {

            val myBitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver,params[0])


            var resimBytes : ByteArray? = null
            for (i in 1..5){

                resimBytes = converToBitmap(myBitmap,100/i)
                publishProgress(resimBytes!!.size.toDouble())
            }

            return resimBytes
        }

        override fun onProgressUpdate(vararg values: Double?) {
            super.onProgressUpdate(*values)


        }

        override fun onPostExecute(result: ByteArray?) {
            super.onPostExecute(result)
            uploadResimFirebaseStorage(result)
        }


    }

    private fun uploadResimFirebaseStorage(result: ByteArray?) {

        if (trafoIsim == "REGEL TRAFO" && !trafoIsim.isNullOrEmpty()){

            val tr1 = trafoIsim?.substring(0..4)
            val tr2 = trafoIsim?.substring(6..10)
            val tr3 = tr1+tr2
            trafoIsim = tr3
            Log.e("regelTrafo","$tr3")

        }else {
            val tr1 = trafoIsim?.substring(0..4)
            val tr2 = trafoIsim?.substring(6)
            val tr3 = tr1+tr2
            trafoIsim = tr3
            Log.e("digerTrafo","$tr3")

        }
        FirebaseStorage.getInstance().reference
            .child("pm3Elektrik").child("Trafo").child(trafoIsim!!)
            .putBytes(result!!)
            .addOnSuccessListener { object : OnSuccessListener<UploadTask.TaskSnapshot>{
                override fun onSuccess(p0: UploadTask.TaskSnapshot?) {

                    val downloadURL = p0?.metadata
                    val downloadURL1 = p0?.storage
                    Log.e("gelenURL","$downloadURL \n $downloadURL1")
                }


            } }

    }

    private fun converToBitmap(myBitmap: Bitmap, i: Int): ByteArray? {

        val stream = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG,i,stream)
        return stream.toByteArray()

    }

    private fun resimKompres(gelenResimYolu: Uri?) {

        val resimKompres = ArkaPlanResimKompres()
        resimKompres.execute(gelenResimYolu)

    }
}
