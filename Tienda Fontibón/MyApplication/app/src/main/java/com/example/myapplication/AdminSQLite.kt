package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLite(contexto: Context, nombre: String, cursor: CursorFactory?, version: Int): SQLiteOpenHelper(contexto, nombre, cursor, version){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table producto(idProducto int primary key, nombre text, precio double)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}