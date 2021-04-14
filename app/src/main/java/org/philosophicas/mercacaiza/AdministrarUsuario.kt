package org.philosophicas.mercacaiza

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth


class AdministrarUsuario : AppCompatActivity() {

    lateinit var volverAtras: TextView
    lateinit var crearUsuario: TextView
    lateinit var identificarUsuario: TextView
    lateinit var correoElectronico: EditText
    lateinit var contrasena: EditText
    lateinit var contrasenaConfirmacion: EditText
    lateinit var cerrarSesion: TextView
    lateinit var preferencias: ManejadorDePreferencias
    lateinit var autorizador: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrar_usuario)

        //Referencia las vistas
        volverAtras = findViewById(R.id.usuarios_volver_atras)
        crearUsuario = findViewById(R.id.usuarios_crear)
        identificarUsuario = findViewById(R.id.usuarios_identificar)
        correoElectronico = findViewById(R.id.usuarios_correo_electronico)
        contrasena = findViewById(R.id.usuarios_contrasena)
        contrasenaConfirmacion = findViewById(R.id.usuarios_contrasena_confirmacion)
        cerrarSesion = findViewById(R.id.usuarios_cerrar_sesion)


        //cofiguraciones visuales:
        volverAtras.paintFlags += Paint.UNDERLINE_TEXT_FLAG
        crearUsuario.paintFlags += Paint.UNDERLINE_TEXT_FLAG
        cerrarSesion.paintFlags += Paint.UNDERLINE_TEXT_FLAG
        identificarUsuario.paintFlags += Paint.UNDERLINE_TEXT_FLAG


        //Manejador de preferencias
        preferencias = ManejadorDePreferencias(this)

        //Autorizador de sesiones
        autorizador = FirebaseAuth.getInstance()

        //Verificamos si existe ya un usuario guardado en la aplicación
        if (preferencias.usuarioId != null) {

            //Si existe llenamos los campos
            correoElectronico.setText(preferencias.correoElectronico)
            contrasena.setText(preferencias.contrasena)

            //inhabilitamos las vistas para que no haya
            //cambios no deseados
            correoElectronico.isEnabled = false
            contrasena.isEnabled = false
            contrasenaConfirmacion.isEnabled = false
        }

        //Configuramos la creación de usuarios
        crearUsuario.setOnClickListener {

            autorizador.createUserWithEmailAndPassword(
                correoElectronico.text.toString().trim(),
                contrasena.text.toString().trim()
            )
                .addOnFailureListener {
                    AlertDialog.Builder(this)
                        .setMessage(it.message!!)
                        .setNeutralButton(R.string.ok) { _, _ -> }
                }

                .addOnSuccessListener {
                    guardarPreferencias()
                    finish()
                }
        }


        //Configuramos la identificación de usuarios
        identificarUsuario.setOnClickListener {

            autorizador.signInWithEmailAndPassword(
                correoElectronico.text.toString().trim(),
                contrasena.text.toString().trim()
            )
                .addOnFailureListener {
                    AlertDialog.Builder(this)
                        .setMessage(it.message!!)
                        .setNeutralButton(R.string.ok) { _, _ -> }
                }

                .addOnSuccessListener {
                    guardarPreferencias()
                    finish()
                }
        }

        //Configuramos la desconexion de usuarios
        cerrarSesion.setOnClickListener {
            autorizador.signOut()
            preferencias.usuarioId = null
            preferencias.contrasena = null
            preferencias.correoElectronico = null
            correoElectronico.isEnabled = true
            contrasena.isEnabled = true
            contrasenaConfirmacion.isEnabled = true


        }

        volverAtras.setOnClickListener {
            finish()
        }


    }


    //Guarda las preferencias
    fun guardarPreferencias() {
        preferencias.usuarioId = autorizador.currentUser?.uid
        preferencias.correoElectronico = correoElectronico.text.toString().trim()
        preferencias.contrasena = contrasena.text.toString().trim()
    }

}