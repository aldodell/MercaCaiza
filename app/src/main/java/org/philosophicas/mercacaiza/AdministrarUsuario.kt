package org.philosophicas.mercacaiza

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdministrarUsuario : AppCompatActivity() {

    lateinit var volverAtras: TextView
    lateinit var crearUsuario: TextView
    lateinit var identificarUsuario: TextView
    lateinit var correoElectronico: EditText
    lateinit var contrasena: EditText
    lateinit var contrasenaConfirmacion: EditText
    lateinit var cerrarSesion: TextView
    lateinit var olvidasteContrasena: TextView
    lateinit var preferencias: ManejadorDePreferencias
    lateinit var autorizador: FirebaseAuth

    val urlDinamycLink =
        "https://mercacaiza.page.link/?link=http://www.google.com&apn=org.philosophicas.mercacaiza"

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
        olvidasteContrasena = findViewById(R.id.usuarios_olvidaste_contrasena)


        //cofiguraciones visuales:
        volverAtras.paintFlags += Paint.UNDERLINE_TEXT_FLAG
        crearUsuario.paintFlags += Paint.UNDERLINE_TEXT_FLAG
        cerrarSesion.paintFlags += Paint.UNDERLINE_TEXT_FLAG
        identificarUsuario.paintFlags += Paint.UNDERLINE_TEXT_FLAG
        olvidasteContrasena.paintFlags += Paint.UNDERLINE_TEXT_FLAG


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
            crearUsuario.isEnabled = false
            identificarUsuario.isEnabled = false
        }


        //Configuramos cuando contraseña pierda foco
        contrasena.setOnFocusChangeListener { _, tieneFoco ->

            if (!tieneFoco) {
                if (contrasena.text.toString().length < 6) {
                    AlertDialog.Builder(this)
                        .setMessage(R.string.reglas_de_contrasena)
                        .setNeutralButton(R.string.ok) { _, _ -> contrasena.requestFocus() }
                        .show()
                }
            }
        }

        contrasenaConfirmacion.setOnFocusChangeListener { _, tieneFoco ->

            if (!tieneFoco) {
                if (contrasena.text != contrasenaConfirmacion.text) {
                    AlertDialog.Builder(this)
                        .setMessage(R.string.contrasena_no_coincide_con_confirmacion)
                        .setNeutralButton(R.string.ok) { _, _ -> }
                        .show()
                }
            }

        }


        //Configuramos la creación de usuarios
        crearUsuario.setOnClickListener {

            //Revisamos que las contraseña y su confirmación
            //coincidan
            if (contrasena.text == contrasenaConfirmacion.text) {
                autorizador.createUserWithEmailAndPassword(
                    correoElectronico.text.toString().trim(),
                    contrasena.text.toString().trim()
                )
                    .addOnFailureListener {
                        AlertDialog.Builder(this)
                            .setMessage(it.message!!)
                            .setNeutralButton(R.string.ok) { _, _ -> }
                            .show()
                    }

                    .addOnSuccessListener {
                        guardarPreferencias()
                        finish()
                    }
            } else {
                //Las contraseñas no coinciden:
                AlertDialog.Builder(this)
                    .setMessage(R.string.contrasena_no_coincide_con_confirmacion)
                    .setNeutralButton(R.string.ok) { _, _ -> }
                    .show()

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
                        .show()
                }

                .addOnSuccessListener {
                    Toast.makeText(
                        this.baseContext,
                        getString(R.string.usuario_valido),
                        Toast.LENGTH_LONG
                    ).show()
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
            crearUsuario.isEnabled = true
            identificarUsuario.isEnabled = true

            AlertDialog.Builder(this)
                .setMessage(R.string.sesion_cerrada)
                .setNeutralButton(R.string.ok) { _, _ -> }
                .show()

        }

        volverAtras.setOnClickListener {
            finish()
        }

        //Configuramos el ovido de contraseña
        olvidasteContrasena.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.instrucciones_regenerar_contrasena)
                .setNegativeButton(R.string.no) { _, _ -> }
                .setPositiveButton(R.string.si) { _, _ ->

                    //Construimos las opciones
                    val actionCodeSettings = ActionCodeSettings.newBuilder()
                        .setAndroidPackageName("org.philosophicas.mercacaiza", true, "23")
                        .setHandleCodeInApp(true)
                        .setUrl(urlDinamycLink)
                        //.setDynamicLinkDomain()
                        .build()

//Pedimos al autorizador que envíe el correo.
                    autorizador.sendPasswordResetEmail(
                        correoElectronico.text.toString().trim(),
                        actionCodeSettings
                    )
                        .addOnFailureListener {
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                            Log.d("aldox", it.message!!)
                        }
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                getString(R.string.correo_enviado),
                                Toast.LENGTH_LONG
                            ).show()

                        }

                }
                .show()

        }


    }


    //Guarda las preferencias
    fun guardarPreferencias() {

        autorizador.currentUser?.let {
            preferencias.usuarioId = it.uid
            preferencias.correoElectronico = correoElectronico.text.toString().trim()
            preferencias.contrasena = contrasena.text.toString().trim()

            val usuario = Usuario.get(
                it.uid,
                correoElectronico.text.toString().trim(),
                contrasena.text.toString()
            )


            val db = FirebaseFirestore.getInstance()
            db.document("usuarios/$it.uid").set(usuario)
                .addOnSuccessListener {
                    Toast.makeText(this,"SUCCESS", Toast.LENGTH_LONG).show()

                }
                .addOnFailureListener {
                    Toast.makeText(this,"ERROR SAVING:${it.message}", Toast.LENGTH_LONG).show()
                }


        }

    }

}