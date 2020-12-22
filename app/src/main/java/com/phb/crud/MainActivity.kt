package com.phb.crud

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.update
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var oldNomorHp = intent.getStringExtra("oldNomorHp")
        var oldNominal = intent.getStringExtra("oldNominal")
        var oldHargaPulsa = intent.getStringExtra("oldHargaPulsa")
        var oldNamaPerdana = intent.getStringExtra("oldNamaPerdana")
        var oldJumlah = intent.getStringExtra("oldJumlah")
        var oldHargaKuota = intent.getStringExtra("oldHargaKuota")



        if (oldNominal.isNullOrBlank()){
            buttonUpdate.isEnabled = false
        }else{
            buttonSimpan.isEnabled = false
            editTextNoHp.setText(oldNomorHp)
            editTextNominal.setText(oldNominal)
            editTextHargaPulsa.setText(oldHargaPulsa)
            editTextNamaPerdana.setText(oldNamaPerdana)
            editTextJumlah.setText(oldJumlah)
            editTextHargaKuota.setText(oldHargaKuota)
        }

        buttonSimpan.setOnClickListener {
            addDataPulsa()
            addDataKuota()

            // clear data
            clearData()
        }

        buttonLihatData.setOnClickListener {
            startActivity<ListPulsaActivity>()
            startActivity<ListKuotaActivity>()
        }

        buttonUpdate.setOnClickListener {
            database.use {
                update(Pulsa.TABLE_PULSA,
                    Pulsa.NOMORHP to editTextNoHp.text.toString(),
                    Pulsa.NOMINAL to editTextNominal.text.toString(),
                    Pulsa.HARGAPULSA to editTextHargaPulsa.text.toString())
                    .whereArgs("${Pulsa.NOMORHP} = {nomorhp}"
                    ).exec()
                update(Kuota.TABLE_KUOTA,
                    Kuota.NAMAPERDANA to editTextNamaPerdana.text.toString(),
                    Kuota.JUMLAH to editTextJumlah.text.toString(),
                    Kuota.HARGAKUOTA to editTextHargaKuota.text.toString())
                    .whereArgs("${Kuota.NAMAPERDANA} = {namaperdana}"
                    ).exec()
            }

            // clear data
            clearData()
            toast("Data Diupdate")
        }
    }

    private fun addDataPulsa() {
        database.use {
            insert(Pulsa.TABLE_PULSA,
                Pulsa.NOMORHP to editTextNoHp.text.toString(),
                Pulsa.NOMINAL to editTextNominal.text.toString(),
                Pulsa.HARGAPULSA to editTextHargaPulsa.text.toString()
            )
            toast("Data berhasil disimpan!")
        }
    }

    private fun addDataKuota() {
        database.use {
            insert(Kuota.TABLE_KUOTA,
                Kuota.NAMAPERDANA to editTextNamaPerdana.text.toString(),
                Kuota.JUMLAH to editTextJumlah.text.toString(),
                Kuota.HARGAKUOTA to editTextHargaKuota.text.toString()
            )
            toast("Data berhasil disimpan!")
        }
    }

    fun clearData(){
        editTextNoHp.text.clear()
        editTextNominal.text.clear()
        editTextHargaPulsa.text.clear()
        editTextNamaPerdana.text.clear()
        editTextJumlah.text.clear()
        editTextHargaKuota.text.clear()
    }
}