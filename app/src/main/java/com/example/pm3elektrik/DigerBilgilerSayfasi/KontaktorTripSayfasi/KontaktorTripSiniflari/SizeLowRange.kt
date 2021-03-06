package com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi.KontaktorTripSiniflari


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pm3elektrik.R
import com.github.chrisbanes.photoview.PhotoView

class SizeLowRange : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_size_low_range, container, false)

        val photoView = view.findViewById<PhotoView>(R.id.photoView)

        photoView.setImageResource(R.drawable.low_range)

        return view
    }


}
