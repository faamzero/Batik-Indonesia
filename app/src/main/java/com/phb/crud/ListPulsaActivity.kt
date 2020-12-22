package com.phb.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_pulsa.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class ListPulsaActivity : AppCompatActivity() {

    private lateinit var adapter: RVAdapterPulsa
    private var pulsa = ArrayList<Pulsa>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pulsa)

        adapter = RVAdapterPulsa(this, pulsa)
        recylerViewPulsa.adapter = adapter

        getData()
        recylerViewPulsa.layoutManager = LinearLayoutManager(this)
    }

    private fun getData() {
        database.use {
            pulsa.clear()
            var result = select(Pulsa.TABLE_PULSA)
            var dataPulsa = result.parseList(classParser<Pulsa>())
            pulsa.addAll(dataPulsa)
            adapter.notifyDataSetChanged()
        }
    }
}