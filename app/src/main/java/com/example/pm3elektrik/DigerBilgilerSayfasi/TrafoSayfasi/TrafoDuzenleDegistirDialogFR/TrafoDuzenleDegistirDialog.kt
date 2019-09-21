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
import com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoModel.TrafoModel

import com.example.pm3elektrik.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.SuccessContinuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_ana_sayfa.*
import java.io.ByteArrayOutputStream

class TrafoDuzenleDegistirDialog : DialogFragment() {

    var galeridenGelenResimYolu : String? = null
    var gelenResimYoluURI : Uri? = null
    private var trafoIsim : String? = null
    val trafoModel = TrafoModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_trafo_duzenle_degistir, container, false)

        val fotoYukle = view.findViewById<Button>(R.id.btnTrafoFotoYukle)
        val ekle = view.findViewById<Button>(R.id.btnTrafoBilgiEkle)

        val bundleTrafoIsim: Bundle? = arguments
        trafoIsim = bundleTrafoIsim?.getString("dialogTrafoDuzenleyeIsimGonder")

        fotoYukle.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, 100)
        }

        ekle.setOnClickListener {
            val degTarihi = view.findViewById<EditText>(R.id.etTrafoDegTarihi).text.toString()
            val not = view.findViewById<EditText>(R.id.etTrafoNot).text.toString()

                trafoModel.trafoDegTarihi = degTarihi
                trafoModel.trafoNot = not


            if (!gelenResimYoluURI?.path.isNullOrBlank()){
                resimKompres(gelenResimYoluURI)
                dismiss()
            }else{

                trafoModel.trafoIsim = trafoIsim!!
                val trafoBoslukYok = trafoIsim!!.replace("\\s".toRegex(), "")
                FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
                    .child("Trafo")
                    .child(trafoBoslukYok)
                    .setValue(trafoModel)

            }

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

        val trafoBoslukYok = trafoIsim!!.replace("\\s".toRegex(), "")
        trafoModel.trafoIsim = trafoIsim!!

        FirebaseStorage.getInstance().reference
            .child("pm3Elektrik").child("Trafo").child(trafoBoslukYok)
            .putBytes(result!!)

        FirebaseStorage.getInstance().reference.child("pm3Elektrik").child("Trafo").child(trafoBoslukYok)
            .downloadUrl.addOnCompleteListener {

            val downloadURL = it.result.toString()
            trafoModel.trafoResimURL = downloadURL
            FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
                .child("Trafo")
                .child(trafoBoslukYok)
                .setValue(trafoModel)
        }
    }

    private fun converToBitmap(myBitmap: Bitmap, i: Int): ByteArray? {

        val stream = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, i, stream)
        return stream.toByteArray()
    }

    private fun resimKompres(gelenResimYolu: Uri?) {

        val resimKompres = ArkaPlanResimKompres()
        resimKompres.execute(gelenResimYolu)

    }
}
