package com.example.pm3elektrik.MotorListeSayfasi

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.MotorEkle
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.SalterEkle
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.MotorListeSayfasi.RVAdapter.MotorRVAdapter
import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.activity_ana_sayfa.*
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_motor_liste.view.*


class MotorListe : Fragment(){


    var motorListesi= ArrayList<MotorModel>()
    lateinit var mFAB_cekmece: FloatingActionButton
    lateinit var mFAB_motor: FloatingActionButton
    lateinit var motorListeLayout : ConstraintLayout
    lateinit var myAdapter : MotorRVAdapter
    val motorListe = ArrayList<MotorModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_motor_liste, containerFragment, false)

        val sync = FirebaseDatabase.getInstance().getReference("kayıtlı_verileri_koru")
        sync.keepSynced(true)

        val motor_ara = view.findViewById<EditText>(R.id.etMotorArama)

        motor_ara.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {

                    val gelenVeri = p0.toString().toUpperCase()
                    val arananlar = ArrayList<MotorModel>()

                    for(gelen in motorListesi){

                        val bulunan = gelen.motorTag.toUpperCase()
                        if(bulunan.contains(gelenVeri.toString())){
                            arananlar.add(gelen)
                        }
                    }
                    myAdapter.gelenMotorTagiFiltrele(arananlar)

                }
            }
        })
        motorListeLayout = view.findViewById(R.id.motorListeLayout)

        fireBaseDBOkunanVeriler(view.context)

        //floating action bar buttonları eklendi
        mFAB_cekmece = view.findViewById(R.id.menu_cekmece)
        mFAB_motor = view.findViewById(R.id.menu_motor)

        mFAB_motor.setOnClickListener {

            changeFragment(MotorEkle())
        }
        mFAB_cekmece.setOnClickListener {

            changeFragment(SalterEkle())
        }

        return view
    }
    private fun fireBaseDBOkunanVeriler(mContext : Context) {

        val ref = FirebaseDatabase.getInstance().reference
        ref.child("pm3Elektrik")
            .child("Motor")
            .addValueEventListener( object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    for(dataGetir in p0.children){

                        val okunanBilgiler = dataGetir.getValue(MotorModel::class.java)

                        motorListesi.add(MotorModel(okunanBilgiler!!.motorTag,okunanBilgiler.motorMCCYeri, okunanBilgiler.motorGucKW, okunanBilgiler.motorDevir))
                    }
                    recyclerAdapter(motorListesi,mContext)

                }

            })
    }

    private fun recyclerAdapter(motorGelenListe : ArrayList<MotorModel> , mRvContext: Context) {


        myAdapter = MotorRVAdapter(motorGelenListe,mRvContext)
        view?.rvMotorListe?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(mRvContext,RecyclerView.VERTICAL,false)
        view?.rvMotorListe?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()

    }

    fun gelenVeriler(motorTag: String, motorMCCYeri: String, motorGucKW: Double, motorDevir: String){

        Log.e("motor_liste","$motorTag $motorMCCYeri $motorGucKW $motorDevir")
        val mContext = view?.context as Context
        motorListe.add(MotorModel(motorTag,motorMCCYeri,motorGucKW,motorDevir))

        myAdapter = MotorRVAdapter(motorListe,mContext)
        view?.rvMotorListe?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(mContext,RecyclerView.VERTICAL,false)
        view?.rvMotorListe?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorListe,fragment,"fragment_motor_liste")
        fragmentTransaction.commit()

    }
}
