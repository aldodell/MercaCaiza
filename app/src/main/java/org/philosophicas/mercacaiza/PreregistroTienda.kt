package org.philosophicas.mercacaiza

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PreregistroTienda.newInstance] factory method to
 * create an instance of this fragment.
 */
class PreregistroTienda : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var llave: EditText
    lateinit var btnRegistrar: Button

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
        val view = inflater.inflate(R.layout.fragment_preregistro_tienda, container, false)
        llave = view.findViewById(R.id.preregistro_llave)
        btnRegistrar = view.findViewById(R.id.preregistro_registrar)
        btnRegistrar.setOnClickListener { registrar() }



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PreregistroTienda.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PreregistroTienda().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    /**
     * Se encargar de obtener el valos que pasó el usuario a la llave
     * y la valida. Luego sincroniza con Firebase y las preferencias locales
     * para pasar al Editor de Tienda
     *
     */
    fun registrar() {

        //TODO: Hacer el proceso de validación de la llave


        //Una vez validada la llave se llama al editor de tienda
        val editor = EditorTienda.newInstance("", "")


        fragmentManager?.beginTransaction()
            ?.replace(R.id.base_contenedor, editor)
            ?.addToBackStack(null)
            ?.commit()

    }


}