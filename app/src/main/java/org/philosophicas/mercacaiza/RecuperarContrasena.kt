package org.philosophicas.mercacaiza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class RecuperarContrasena : AppCompatActivity() {

    lateinit var contrasena: EditText
    lateinit var contrasenaConfirmacion: EditText
    lateinit var renovar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasena)

        //Obtenemos las referencias de las vistas
        contrasena = findViewById(R.id.recuperarContrasena)
        contrasenaConfirmacion = findViewById(R.id.recuperarContrasenaConfirmacion)
        renovar = findViewById(R.id.recuperarContrasenaActuar)

        //Configuramos:
        renovar.setOnClickListener {
            val c1 = contrasena.text.toString().trim()
            val c2 = contrasenaConfirmacion.text.toString().trim()


            //Verificamos que la contraseña tenga al menos 6 caracteres:
            if (c1.length < 6) {
                AlertDialog.Builder(this)
                    .setMessage(R.string.reglas_de_contrasena)
                    .setNeutralButton(R.string.ok) { _, _ -> }
                    .show()

            } else {

                //Verificamos que la contraseña y su confirmación sean iguales
                if (c1 != c2) {

                    AlertDialog.Builder(this)
                        .setMessage(R.string.contrasena_no_coincide_con_confirmacion)
                        .setNeutralButton(R.string.ok) { _, _ -> }
                        .show()

                } else {
                    //TODO: Validar usuario y guardar contraseña localmente

                }
            }
        }


        //Obtenemos la información del vínculo Dynamics Link
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnFailureListener {
                Toast.makeText(this, it.message!!, Toast.LENGTH_LONG).show()
            }
            .addOnSuccessListener {
                Log.d("aldox", it.link!!.userInfo!!)
            }

    }
}