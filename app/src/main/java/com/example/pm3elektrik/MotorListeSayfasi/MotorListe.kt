package com.example.pm3elektrik.MotorListeSayfasi


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteEkle.DriveUniteEkle
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.CekmeceEkle
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.MotorEkle
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorveSalterEtiketleri.MotorVeSalterEtiket
import com.example.pm3elektrik.MotorListeSayfasi.RVAdapter.MotorRVAdapter
import kotlinx.android.synthetic.main.activity_ana_sayfa.*
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_motor_liste.view.*
import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.fragment_motor_liste.*


class MotorListe : Fragment() {

    lateinit var mFAB_cekmece: FloatingActionButton
    lateinit var mFAB_motor: FloatingActionButton
    lateinit var mFAB_drive: FloatingActionButton
    lateinit var myAdapter : MotorRVAdapter
    var motorListesi= ArrayList<MotorModel>()
//    lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_motor_liste, containerFragment, false)

//        swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)

        pendingIntentAnaSayfadanGelen()

        fireBaseDBOkunanVeriler(view.context)

//        swipeRefresh.setOnRefreshListener (this)

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
                    }else{
                        Toast.makeText(context?.applicationContext,"Liste Yüklenemedi",Toast.LENGTH_LONG).show()
                    }

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        //Floating Action Bar Butonları ---------------------------------------------------->
        mFAB_cekmece = view.findViewById(R.id.menu_cekmece)
        mFAB_motor = view.findViewById(R.id.menu_motor)
        mFAB_drive = view.findViewById(R.id.menu_drive_motor)

        mFAB_motor.setOnClickListener {
            changeFragment(MotorEkle())
        }
        mFAB_cekmece.setOnClickListener {

            changeFragment(CekmeceEkle())
        }
        mFAB_drive.setOnClickListener {
            changeFragment(DriveUniteEkle())
        }
        //Floating Action Bar Butonları ----------------------------------------------------<

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

                        motorListesi.add(MotorModel(okunanBilgiler!!.motorTag,okunanBilgiler.motorMCCYeri, okunanBilgiler.motorGucKW, okunanBilgiler.motorDevir , okunanBilgiler.cekmeceModel,okunanBilgiler.cekmeceMarka,okunanBilgiler.cekmeceKapasite,okunanBilgiler.motorGelenVeri , okunanBilgiler.motorGucKVA,okunanBilgiler.cekmeceUid))

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

    }
    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.containerMotorListe,fragment,"fragment_motor_liste")
        fragmentTransaction?.commit()
    }
    private fun pendingIntentAnaSayfadanGelen(){

        val bundle :Bundle? = arguments
        val motorTag = bundle?.getString("anaSayfadanGelenMotorTag")

        if (motorTag != null){

            val bundleMotorListe = Bundle()
            bundleMotorListe.putString("rvGelenMotorTag",motorTag)
            val fragment = MotorVeSalterEtiket()
            fragment.arguments = bundleMotorListe
            val transaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.containerMotorListe,fragment,"fragment_motor_liste")?.commit()

        }

    }

//    override fun onRefresh() {
//
//        //fireBaseDBOkunanVeriler(view!!.context)
//
//        Handler().postDelayed(object : Runnable {
//            override fun run() {
//
//                swipeRefresh.isRefreshing = false
//            }
//        },1200)
//    }
}
