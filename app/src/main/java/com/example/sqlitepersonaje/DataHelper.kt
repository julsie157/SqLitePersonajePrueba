package com.example.sqlitepersonaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "Personajes.db"
        private const val TABLA_PERSONAJES = "personajes"
        private const val KEY_ID = "_id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_PESO_MOCHILA = "pesoMochila"
        private const val COLUMN_ESTADOVITAL = "estadoVital"
        private const val COLUMN_RAZA = "raza"
        private const val COLUMN_CLASE = "clase"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLA_PERSONAJES ($KEY_ID INTEGER PRIMARY KEY, $COLUMN_NOMBRE TEXT, $COLUMN_PESO_MOCHILA INTEGER, $COLUMN_ESTADOVITAL TEXT, $COLUMN_RAZA TEXT, $COLUMN_CLASE TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PERSONAJES")
        onCreate(db)
    }

    fun insertarPersonaje(personaje: Personaje):Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, personaje.getNombre())
            put(COLUMN_PESO_MOCHILA, personaje.getPesoMochila())
            put(COLUMN_ESTADOVITAL, personaje.getEstadoVital())
            put(COLUMN_RAZA, personaje.getRaza())
            put(COLUMN_CLASE, personaje.getClase())
        }
        val id= db.insert(TABLA_PERSONAJES, null, values)
        db.close()
        return id
    }



    @SuppressLint("Range")
    fun getPersonas(): ArrayList<Personaje> {
        val personajes = ArrayList<Personaje>()
        val selectQuery = "SELECT * FROM $TABLA_PERSONAJES"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val pesoMochila = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO_MOCHILA))
                val estadoVital = cursor.getString(cursor.getColumnIndex(COLUMN_ESTADOVITAL))
                val clase = cursor.getString(cursor.getColumnIndex(COLUMN_CLASE))
                val raza = cursor.getString(cursor.getColumnIndex(COLUMN_RAZA))
                personajes.add(Personaje(nombre, pesoMochila, estadoVital, clase, raza))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return personajes
    }

}


