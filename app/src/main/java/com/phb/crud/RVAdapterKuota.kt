package com.phb.crud

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list.view.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class RVAdapterKuota(val context: Context, val items: ArrayList<Kuota>) : RecyclerView.Adapter<RVAdapterKuota.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItem(items: Kuota){
            itemView.namaPerdana.text = items.namaperdana
            itemView.nominal.text = items.jumlah
            itemView.hargaPulsa.text = items.hargakuota

            itemView.btnEdit.setOnClickListener {
                itemView.context.startActivity<MainActivity>(
                    "oldNamaPerdana" to items.namaperdana,
                    "oldJumlah" to items.jumlah,
                    "oldHargaKuota" to items.hargakuota
                )
            }

            itemView.btnHapus.setOnClickListener {
                itemView.context.database.use {
                    delete(Kuota.TABLE_KUOTA, "(${Kuota.NAMAPERDANA} = {namaperdana})",
                        "namaperdana" to items.namaperdana.toString())
                }
                itemView.context.toast("Data Dihapus")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position])
    }
}