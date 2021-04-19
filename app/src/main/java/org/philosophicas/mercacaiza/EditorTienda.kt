package org.philosophicas.mercacaiza

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [EditorTienda.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditorTienda() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var cargarImagen: ImageButton
    private lateinit var hp: HiperLinkTextView

    private val OBTENER_IMAGEN = 100

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
        cargarImagen = view.findViewById(R.id.editor_tienda_imagen)
        hp = view.findViewById(R.id.hp)
        hp.setOnClickListener {
            Log.d("aldox", "hola mundo")
        }

        //Configuramos el botón de carga de imagen
        cargarImagen.setOnClickListener {
            // val intento = Intent(Intent.ACTION_GET_CONTENT)
            val intento = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )

            intento.type = "image/*"
            intento.putExtra("crop", "true")
            intento.putExtra("scale", true)
            intento.putExtra("outputX", 256)
            intento.putExtra("outputY", 256)
            intento.putExtra("aspectX", 1)
            intento.putExtra("aspectY", 1)
            intento.putExtra("return-data", true)
            startActivityForResult(intento, OBTENER_IMAGEN)

        }





        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            OBTENER_IMAGEN -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        if (data.extras != null) {
                            val bitmap = data.extras!!.getParcelable<Bitmap>("data")
                            cargarImagen.setImageBitmap(bitmap)
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