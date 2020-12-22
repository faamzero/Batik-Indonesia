package com.phb.crud

data class Kuota(var id: Long?, var namaperdana: String?, var jumlah: String?, var hargakuota: String?){
    companion object{
        const val TABLE_KUOTA: String = "TABLE_KUOTA"
        const val ID: String = "ID_"
        const val NAMAPERDANA: String = "NAMAPERDANA"
        const val JUMLAH: String = "JUMLAH"
        const val HARGAKUOTA: String = "HARGAKUOTA"
    }
}