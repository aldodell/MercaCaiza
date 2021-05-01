package org.philosophicas.mercacaiza

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [EditorTienda.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditorTienda : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var editor_tienda_imagen: ImageButton
    private lateinit var editor_tienda_nombre: EditText
    private lateinit var editor_tienda_palabras_clave: EditText
    private lateinit var editor_tienda_direccion_fisica: EditText
    private lateinit var editor_tienda_telefono: EditText
    private lateinit var editor_tienda_nombre_responsable: EditText
    private lateinit var editor_tienda_cedula_rif: EditText
    private lateinit var editor_tienda_cedula_rif_imagen: ImageButton
    private lateinit var editor_tienda_imagen_rotar: ImageButton
    private lateinit var editor_tienda_cedula_imagen_rotar: ImageButton
    private lateinit var editor_tienda_guardar: Button

    private val OBTENER_IMAGEN_TIENDA = 100
    private val OBTENER_IMAGEN_CEDULA = 101

    private var tiendaBitmap: Bitmap? = null
    private var cedulaBitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_editor_tienda, container, false)
        editor_tienda_imagen = view.findViewById(R.id.editor_tienda_imagen)
        editor_tienda_nombre = view.findViewById(R.id.editor_tienda_nombre)
        editor_tienda_palabras_clave = view.findViewById(R.id.editor_tienda_palabras_clave)
        editor_tienda_direccion_fisica = view.findViewById(R.id.editor_tienda_direccion_fisica)
        editor_tienda_telefono = view.findViewById(R.id.editor_tienda_telefono)
        editor_tienda_nombre_responsable = view.findViewById(R.id.editor_tienda_nombre_responsable)
        editor_tienda_cedula_rif = view.findViewById(R.id.editor_tienda_cedula_rif)
        editor_tienda_cedula_rif_imagen = view.findViewById(R.id.editor_tienda_cedula_rif_imagen)
        editor_tienda_imagen_rotar = view.findViewById(R.id.editor_tienda_imagen_rotar)
        editor_tienda_cedula_imagen_rotar = view.findViewById(R.id.editor_tienda_cedula_imagen_rotar)
        editor_tienda_guardar = view.findViewById(R.id.editor_tienda_guardar)


        //Configuramos el botón de carga de imagen para la tienda
        editor_tienda_imagen.setOnClickListener {
            val intento = Intent(
                    Intent.ACTION_GET_CONTENT

            )
            intento.type = "image/*"
            startActivityForResult(intento, OBTENER_IMAGEN_TIENDA)
        }


        //Configuramos el botón de carga de imagen para la cedula
        editor_tienda_cedula_rif_imagen.setOnClickListener {
            val intento = Intent(
                    Intent.ACTION_GET_CONTENT
            )
            intento.type = "image/*"
            startActivityForResult(intento, OBTENER_IMAGEN_CEDULA)

        }

        //rotación del logo de la tienda
        editor_tienda_imagen_rotar.setOnClickListener {
            tiendaBitmap?.let {
                tiendaBitmap = rotateImage(tiendaBitmap!!, 90F)
                editor_tienda_imagen.setImageBitmap(tiendaBitmap)
            }
        }


        //rotación del logo de la tienda
        editor_tienda_cedula_imagen_rotar.setOnClickListener {
            cedulaBitmap?.let {
                cedulaBitmap = rotateImage(cedulaBitmap!!, 90F)
                editor_tienda_cedula_rif_imagen.setImageBitmap(cedulaBitmap)
            }
        }

        //Guardamos la información
        editor_tienda_guardar.setOnClickListener {
            guardar()
        }



        return view
    }


    fun guardar() {
        val db = FirebaseFirestore.getInstance()
        val usuarioId = ManejadorDePreferencias(this.requireContext()).usuarioId
        if (usuarioId != null) {

            val tienda = Tienda().apply {
                nombre = editor_tienda_nombre.text.toString()
                palabrasClaves = editor_tienda_palabras_clave.text.toString()
                direccionFisica = editor_tienda_direccion_fisica.text.toString()
                telefono = editor_tienda_telefono.text.toString()
                personaResponsable = editor_tienda_nombre_responsable.text.toString()
                cedulaRif = editor_tienda_cedula_rif.text.toString()

            }

            val tiendaRef = db.document("usuarios/${usuarioId}/tiendas/0")
            tiendaRef.set(tienda.map)
                    .addOnSuccessListener {
                        AlertDialog.Builder(this.requireContext())
                                .setMessage(getString(R.string.ok))
                                .setNeutralButton(R.string.ok) { _, _ -> }
                                .show()

                    }
                    .addOnFailureListener {
                        AlertDialog.Builder(this.requireContext())
                                .setMessage("${getString(R.string.error_al_guardar)}:${it.message!!}")
                                .setNeutralButton(R.string.ok) { _, _ -> }
                                .show()
                    }

        } else {
            AlertDialog.Builder(this.requireContext())
                    .setMessage("Usuario sin validar")
                    .setNeutralButton(R.string.ok) { _, _ -> }
                    .show()
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            OBTENER_IMAGEN_TIENDA -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = data?.data as Uri
                    uri.let {
                        tiendaBitmap = darFormatoImagen(this.requireContext(), it, 720)
                        tiendaBitmap?.let { im ->
                            editor_tienda_imagen.setImageBitmap(im)
                        }


                    }
                }
            }

            OBTENER_IMAGEN_CEDULA -> {

                if (resultCode == Activity.RESULT_OK) {
                    val uri = data?.data as Uri
                    uri.let {
                        cedulaBitmap = darFormatoImagen(this.requireContext(), it, 720)
                        cedulaBitmap?.let { im ->
                            editor_tienda_cedula_rif_imagen.setImageBitmap(im)
                        }


                    }

                }
            }


            else -> {
            }
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param llave Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditorTienda.
         */
// TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(llave: String, param2: String) =
                EditorTienda().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, llave)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}