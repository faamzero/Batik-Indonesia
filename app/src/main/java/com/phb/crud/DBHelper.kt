package com.phb.crud

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx, "Pulsa.db, Kuota.db", null, 1) {
    companion object{
        private var instance: DBHelper? = null
        @Synchronized
        fun getInstance(ctx: Context) : DBHelper{

                if(instance == null){
                    instance = DBHelper(ctx.applicationContext)
                }
                return instance as DBHelper
            }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(Pulsa.TABLE_PULSA, true,
        Pulsa.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
        Pulsa.NOMORHP to TEXT,
        Pulsa.NOMINAL to TEXT,
        Pulsa.HARGAPULSA to TEXT
        )
        db?.createTable(Kuota.TABLE_KUOTA, true,
            Kuota.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Kuota.NAMAPERDANA to TEXT,
            Kuota.JUMLAH to TEXT,
            Kuota.HARGAKUOTA to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Pulsa.TABLE_PULSA, true)
        db?.dropTable(Kuota.TABLE_KUOTA, true)
    }
}

val Context.database : DBHelper
get() = DBHelper.getInstance(applicationContext)