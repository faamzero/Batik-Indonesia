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

class RVAdapterPulsa(val context: Context, val items: ArrayList<Pulsa>) : RecyclerView.Adapter<RVAdapterPulsa.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItem(items: Pulsa){
            itemView.noHp.text = items.nomorhp
            itemView.nominal.text = items.nominal
            itemView.hargaPulsa.text = items.hargapulsa

            itemView.btnEdit.setOnClickListener {
                itemView.context.startActivity<MainActivity>(
                    "oldNoHp" to items.nomorhp,
                    "oldNominal" to items.nominal,
                    "oldHargaPulsa" to items.hargapulsa
                )
            }

            itemView.btnHapus.setOnClickListener {
                itemView.context.database.use {
                    delete(Pulsa.TABLE_PULSA, "(${Pulsa.NOMORHP} = {nomorhp})",
                        "nomorhp" to items.nomorhp.toString())
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