package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ListaProductos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos)

        listViewProductos = findViewById(R.id.ListViewProductos)
        listaProductos = ArrayList()

        consultarListaProductos()

    }

    fun cambiarActivty() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    lateinit var listViewProductos: ListView
    lateinit var listaProductos: ArrayList<List<String>> // Corrección aquí

    fun consultarListaProductos() {
        val adminSQLite = AdminSQLite(this, "Tienda", null, 1)
        val bd = adminSQLite.writableDatabase

        val consulta = bd.rawQuery("select * from producto", null)

        while (consulta.moveToNext()) {
            val producto = listOf(
                consulta.getString(0),
                consulta.getString(1),
                consulta.getString(2)
            )
            listaProductos.add(producto)
        }

        // Crear un ArrayList para contener las representaciones de cadena de los productos
        val productosString = ArrayList<String>()

        // Convertir la lista de productos en representaciones de cadena
        for (producto in listaProductos) {
            val productoString = "${producto[0]} - ${producto[1]} - ${producto[2]}"
            productosString.add(productoString)
        }

        // Crear un ArrayAdapter y establecerlo en el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, productosString)
        listViewProductos.adapter = adapter

        consulta.close()
    }
}

