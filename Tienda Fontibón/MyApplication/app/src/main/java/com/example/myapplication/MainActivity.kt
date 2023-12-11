package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val idProducto = findViewById<EditText>(R.id.editTextIdProducto)
        val nombre = findViewById<EditText>(R.id.editTextNombre)
        val precio = findViewById<EditText>(R.id.editTextPrecio)
        val botonRegistrar = findViewById<Button>(R.id.buttonRegistrar)
        val botonConsultar = findViewById<Button>(R.id.buttonConsultar)
        val botonEditar = findViewById<Button>(R.id.buttonEditar)
        val botonEliminar = findViewById<Button>(R.id.buttonEliminar)
        val botonListar = findViewById<Button>(R.id.buttonListar)

        botonRegistrar.setOnClickListener {
            val adminSQLite = AdminSQLite(this, "Tienda", null, 1) // Parámetros de la base de datos.
            val bd = adminSQLite.writableDatabase // Se asignan elementos db que es para escribir en la base de datos.
            val registro = ContentValues()
            registro.put("idProducto", idProducto.text.toString())
            registro.put("nombre", nombre.text.toString())
            registro.put("precio", precio.text.toString())

            bd.insert("producto", null, registro)
            bd.close()

            idProducto.setText("")
            nombre.setText("")
            precio.setText("")

            Toast.makeText(this, "El producto fué registrado!", Toast.LENGTH_SHORT).show()
        }

        botonConsultar.setOnClickListener {
            val adminSQLite = AdminSQLite(this, "Tienda", null, 1)
            val bd = adminSQLite.writableDatabase
            val consulta = bd.rawQuery("select nombre, precio from producto where idProducto = ${idProducto.text.toString()}", null)

            if (consulta.moveToFirst()) {
                nombre.setText(consulta.getString(0))
                precio.setText((consulta.getString(1)))
            } else {
                Toast.makeText(this, "El producto no existe", Toast.LENGTH_SHORT).show()
            }
            bd.close()
        }

        botonEditar.setOnClickListener {
            val adminSQLite = AdminSQLite(this, "Tienda", null, 1)
            val bd = adminSQLite.writableDatabase
            val registro = ContentValues()
            registro.put("nombre", nombre.text.toString())
            registro.put("precio", precio.text.toString())

            val editar = bd.update("producto", registro, "idProducto=${idProducto.text.toString()}", null)
            bd.close()

            if (editar == 1) {
                Toast.makeText(this, "Producto modificado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se puede modificar el producto", Toast.LENGTH_SHORT).show()
            }
        }

        botonEliminar.setOnClickListener {
            val adminSQLite = AdminSQLite(this, "Tienda", null, 1)
            val bd = adminSQLite.writableDatabase
            val eliminar = bd.delete("producto", "idProducto=${idProducto.text.toString()}", null)
            bd.close()

            idProducto.setText("")
            nombre.setText("")
            precio.setText("")

            if (eliminar == 1) {
                Toast.makeText(this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se pudo eliminar el producto", Toast.LENGTH_SHORT).show()
            }
        }

        botonListar.setOnClickListener{
            val intent = Intent(this, ListaProductos::class.java)
            startActivity(intent)
        }
    }
}