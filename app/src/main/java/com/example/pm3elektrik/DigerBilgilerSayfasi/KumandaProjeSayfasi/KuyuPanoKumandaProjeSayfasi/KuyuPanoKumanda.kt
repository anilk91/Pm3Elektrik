package com.example.pm3elektrik.DigerBilgilerSayfasi.KumandaProjeSayfasi.KuyuPanoKumandaProjeSayfasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pm3elektrik.R
import com.github.chrisbanes.photoview.PhotoView

class KuyuPanoKumanda : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kuyu_pano_kumanda, container, false)

        val photoView = view.findViewById<PhotoView>(R.id.photoView)

        photoView.setImageResource(R.mipmap.kuyu_pano_kumanda_projesi)

        return view
    }
}
