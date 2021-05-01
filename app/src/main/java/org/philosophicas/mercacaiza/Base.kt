package org.philosophicas.mercacaiza

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.InputStreamReader
import java.lang.Exception
import java.util.concurrent.Executor

class Base : AppCompatActivity() {

    lateinit var bnv: BottomNavigationView
    lateinit var contenedor: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        //Obtenemos las referencias a las vistas
        bnv = findViewById(R.id.baseBNV)
        contenedor = findViewById(R.id.base_contenedor)

        //Manejador de preferencias
        val preferencias = ManejadorDePreferencias(this.baseContext)

        //Configuramos el botom navigation view
        bnv.setOnNavigationItemSelectedListener(object :
                BottomNavigationView.OnNavigationItemSelectedListener {
            /**
             * Called when an item in the bottom navigation menu is selected.
             *
             * @param item The selected item
             * @return true to display the item as the selected item and false if the item should not be
             * selected. Consider setting non-selectable items as disabled preemptively to make them
             * appear non-interactive.
             */
            override fun onNavigationItemSelected(item: MenuItem): Boolean {


                when (item.itemId) {

                    R.id.menu_base_productos -> {

                    }

                    R.id.menu_base_tiendas -> {

                    }


                    R.id.menu_base_mi_tienda -> {

                        //Si no hay registrada una tienda entonces damos la posibilidad de registrar
                        if (preferencias.tiendaId == null) {
                            abrirFragmento(PreregistroTienda.newInstance("", ""))
                        }

                    }

                    R.id.menu_base_ajustes -> {
                        abrirFragmento(EditorTienda.newInstance("", ""))
                    }

                    else -> return false

                }

                return true
            }

        })


    }


    private fun abrirFragmento(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.base_contenedor, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}