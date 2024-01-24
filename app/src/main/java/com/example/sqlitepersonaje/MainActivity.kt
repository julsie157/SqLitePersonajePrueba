package com.example.sqlitepersonaje


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Creamos la instancia de la clase com.example.sqlitepersonaje.DatabaseHelper, nos va a permitir acceder a los recursos de la aplicación
        val dbHelper = DatabaseHelper(this)
        val btnGuardarPersonaje = findViewById<Button>(R.id.btnGuardarPersoanje)
        val btnRecuperarPersonaje = findViewById<Button>(R.id.btnRecuperarpersonaje)

        btnGuardarPersonaje.setOnClickListener {
            //Recogemos los datos de los EditText
            val nombre = findViewById<EditText>(R.id.nombre).text.toString()
            val pesoMochila = findViewById<EditText>(R.id.pesoMochila).text.toString().toInt()
            val estadoVital = findViewById<EditText>(R.id.estadoVital).text.toString()
            val clase = findViewById<EditText>(R.id.pesoMochila).text.toString()
            val raza = findViewById<EditText>(R.id.raza).text.toString()
            val personaje = Personaje(nombre, pesoMochila, estadoVital, clase, raza)
            dbHelper.insertarPersonaje(personaje)
            Toast.makeText(this, "Persona guardada en la base de datos SQLite", Toast.LENGTH_SHORT).show()
        }
        btnRecuperarPersonaje.setOnClickListener {
            val selectorPersonaje = findViewById<EditText>(R.id.selectorPersonaje).text.toString().toInt()
            val nombreRec = findViewById<TextView>(R.id.nombreRec)
            val pesoMochilaRec = findViewById<TextView>(R.id.pesoMochilaRec)
            val estadoVitalRec = findViewById<TextView>(R.id.estadoVitalRec)
            val claseRec = findViewById<TextView>(R.id.claseRec)
            val razaRec = findViewById<TextView>(R.id.razaRec)
            val personajes = dbHelper.getPersonas()
            nombreRec.text = personajes[selectorPersonaje].getNombre()
            pesoMochilaRec.text = personajes[selectorPersonaje].getPesoMochila().toString()
            estadoVitalRec.text = personajes[selectorPersonaje].getEstadoVital()
            claseRec.text = personajes[selectorPersonaje].getClase()
            razaRec.text = personajes[selectorPersonaje].getRaza()

        }
    }
}
data class Personaje(
    private var nombre: String,
    private var pesoMochila: Int,
    private var estadoVital: String,
    private var raza: String,
    private var clase: String,

    ) {
    private var id:Int = 0
    fun getNombre(): String {
        return nombre
    }
    fun getPesoMochila(): Int {
        return pesoMochila
    }
    fun getEstadoVital(): String {
        return estadoVital
    }
    fun getRaza(): String {
        return raza
    }
    fun getClase(): String {
        return clase
    }
    fun setId(id:Int){
        this.id=id
    }
}