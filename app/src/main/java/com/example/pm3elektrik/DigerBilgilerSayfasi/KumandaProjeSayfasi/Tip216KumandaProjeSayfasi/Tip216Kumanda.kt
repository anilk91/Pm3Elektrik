package com.example.pm3elektrik.DigerBilgilerSayfasi.KumandaProjeSayfasi.Tip216KumandaProjeSayfasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pm3elektrik.R
import com.github.chrisbanes.photoview.PhotoView

class Tip216Kumanda : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tip216_kumanda, container, false)

        val photoView = view.findViewById<PhotoView>(R.id.photoView)

        photoView.setImageResource(R.mipmap.tip_216_kumanda_projesi)

        return view
    }

}
