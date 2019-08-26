package com.example.pm3elektrik.MotorListeSayfasi

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pm3elektrik.MotorListeSayfasi.DuzenleFragmentleri.MotorEtiketDuzenle
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.MotorEkle
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.SalterEkle
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorveSalterEtiketleri.MotorVeSalterEtiket
import com.example.pm3elektrik.MotorListeSayfasi.RVAdapter.MotorRVAdapter
import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.activity_ana_sayfa.*
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_motor_liste.*
import kotlinx.android.synthetic.main.fragment_motor_liste.view.*


class MotorListe : Fragment() {

    lateinit var mFAB_cekmece: FloatingActionButton
    lateinit var mFAB_motor: FloatingActionButton
    lateinit var motorListeLayout : ConstraintLayout
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
                        if(bulunan.contains(gelenVeri.toString())){
                            arananlar.add(gelen)
                        }
                    }
                    myAdapter.gelenMotorTagiFiltrele(arananlar)

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        motorListeLayout = view.findViewById(R.id.motorListeLayout)


        //Floatin Action Bar Butonları ----------------------------------------------------
        mFAB_cekmece = view.findViewById(R.id.menu_cekmece)
        mFAB_motor = view.findViewById(R.id.menu_motor)

        mFAB_motor.setOnClickListener {

            changeFragment(MotorEkle())

        }
        mFAB_cekmece.setOnClickListener {

            changeFragment(SalterEkle())
        }
        //Floatin Action Bar Butonları ----------------------------------------------------

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

                        motorListesi.add(MotorModel(okunanBilgiler!!.motorTag,okunanBilgiler.motorMCCYeri, okunanBilgiler.motorGucKW, okunanBilgiler.motorDevir))
                    }
                    recyclerAdapter(motorListesi,mContext)

                }

            })
    }

    //FirebaseDatabase Okunan Değerlerden Gelen Veriler
    fun recyclerAdapter(motorGelenListe : ArrayList<MotorModel> , mRvContext: Context) {
        myAdapter = MotorRVAdapter(motorGelenListe,mRvContext)
        view?.rvMotorListe?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(mRvContext,RecyclerView.VERTICAL,false)
        view?.rvMotorListe?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()
    }

    //Motor Ekleden Gelen Veriler
    fun gelenVeriler(motorTag: String, motorMCCYeri: String, motorGucKW: Double, motorDevir: String , mContext: Context): ArrayList<MotorModel> {

        Log.e("motorliste","$motorTag $motorDevir $motorGucKW $motorMCCYeri")
        motorListesi.add(MotorModel(motorTag,motorMCCYeri,motorGucKW,motorDevir))

        myAdapter = MotorRVAdapter(motorListesi,mContext)
        view?.rvMotorListe?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(mContext,RecyclerView.VERTICAL,false)
        view?.rvMotorListe?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()

        return motorListesi
    }

    fun motorListedenEtiketBaslat(motorTag : String){

       // MotorVeSalterEtiket().recyclerAdapterGelenTag(motorTag)
        val motorvesalter = MotorVeSalterEtiket()
        val fragmentTransaction :FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.containerMotorListe,motorvesalter,"fragment_motor_listesi" )
        fragmentTransaction?.commit()


    }
    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorListe,fragment,"fragment_motor_liste")
        fragmentTransaction.commit()
    }
}
