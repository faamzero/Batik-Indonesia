package com.phb.crud

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_kuota.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class ListKuotaActivity : AppCompatActivity() {
    private lateinit var adapter: RVAdapterKuota
    private var kuota = ArrayList<Kuota>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_kuota)

        adapter = RVAdapterKuota(this, kuota)
        recylerViewKuota.adapter = adapter

        getData()
        recylerViewKuota.layoutManager = LinearLayoutManager(this)
    }

    private fun getData() {
        database.use {
            kuota.clear()
            var result = select(Kuota.TABLE_KUOTA)
            var dataKuota = result.parseList(classParser<Kuota>())
            kuota.addAll(dataKuota)
            adapter.notifyDataSetChanged()
        }
    }

}