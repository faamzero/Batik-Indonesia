package com.phb.crud

data class Pulsa(var id: Long?, var nomorhp: String?, var nominal: String?, var hargapulsa: String?){
    companion object{
        const val TABLE_PULSA: String = "TABLE_PULSA"
        const val ID: String = "ID_"
        const val NOMORHP: String = "NOMORHP"
        const val NOMINAL: String = "NOMINAL"
        const val HARGAPULSA: String = "HARGAPULSA"
    }
}