package com.example.pm3elektrik.MotorListeSayfasi

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.CekmeceEkle
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.MotorEkle
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.MotorListeSayfasi.RVAdapter.MotorRVAdapter
import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.activity_ana_sayfa.*
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_motor_liste.view.*


class MotorListe : Fragment() {

    lateinit var mFAB_cekmece: FloatingActionButton
    lateinit var mFAB_motor: FloatingActionButton
    lateinit var myAdapter : MotorRVAdapter
    var motorListesi= ArrayList<MotorModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_motor_liste, containerFragment, false)

        fireBaseDBOkunanVeriler(view.context)

        val sync = FirebaseDatabase.getInstance().getReference("kayıtlı_verileri_koru")
        sync.keepSynced(true)


        val motor_ara = view.findViewById<EditText>(R.id.etMotorArama)
        motor_ara.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {

                    val gelenVeri = p0.toString().toUpperCase()
                    val arananlar = ArrayList<MotorModel>()

                    for(gelen in motorListesi){

                        val bulunan = gelen.motorTag.toUpperCase()
                        if(bulunan.contains(gelenVeri)){
                            arananlar.add(gelen)
                        }
                    }
                    if(myAdapter != null){
                        myAdapter.gelenMotorTagiFiltrele(arananlar)
                    }

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        //Floating Action Bar Butonları ---------------------------------------------------->
        mFAB_cekmece = view.findViewById(R.id.menu_cekmece)
        mFAB_motor = view.findViewById(R.id.menu_motor)

        mFAB_motor.setOnClickListener {
            changeFragment(MotorEkle())
        }
        mFAB_cekmece.setOnClickListener {

            changeFragment(CekmeceEkle())
        }
        //Floating Action Bar Butonları ----------------------------------------------------<

        return view
    }
    private fun fireBaseDBOkunanVeriler(mContext : Context) {

        val ref = FirebaseDatabase.getInstance().reference
        ref.child("pm3Elektrik")
            .child("Motor")
            .addListenerForSingleValueEvent( object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    for(dataGetir in p0.children){

                        val okunanBilgiler = dataGetir.getValue(MotorModel::class.java)

                        motorListesi.add(MotorModel(okunanBilgiler!!.motorTag,okunanBilgiler.motorMCCYeri, okunanBilgiler.motorGucKW, okunanBilgiler.motorDevir , okunanBilgiler.cekmeceModel,okunanBilgiler.cekmeceMarka,okunanBilgiler.cekmeceKapasite,okunanBilgiler.motorGelenVeri))

                    }
                    recyclerAdapter(motorListesi,mContext)
                }
            })
    }

    //FirebaseDatabase Okunan Değerlerden Gelen Veriler
    fun recyclerAdapter(motorGelenListe: ArrayList<MotorModel>, mContext: Context) {

        myAdapter = MotorRVAdapter(motorGelenListe,mContext,activity)
        view?.rvMotorListe?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(mContext,RecyclerView.VERTICAL,false)
        view?.rvMotorListe?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()
    }
    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.containerMotorListe,fragment,"fragment_motor_liste")
        fragmentTransaction?.commit()
    }
}
