package org.philosophicas.mercacaiza

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {


    //TEMPORAL:
    lateinit var boton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        //Obtenemos las preferencias
        val preferencias = ManejadorDePreferencias(this)

        //Si no hay un usuario registrado, guardado en las preferencias
        //Entonces pedimos que o bien se cree un usuario nuevo
        //O se valide uno existente.

        if (preferencias.usuarioId == null) {
            val intento = Intent(this, AdministrarUsuario::class.java)
            startActivity(intento)
        }


        //TEMPORAL
        boton = findViewById(R.id.button)
        boton.setOnClickListener {
            startActivity(Intent(this, AdministrarUsuario::class.java))
        }


        //Vamos al intento base
        startActivity(Intent(this, Base::class.java))





    }
}