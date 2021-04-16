package org.philosophicas.mercacaiza

import android.content.Context


/**
 * Clase que administra las preferencias del usuario
 */
class ManejadorDePreferencias(contexto: Context) {

    private val prefs = contexto.getSharedPreferences("preferencias", Context.MODE_PRIVATE)

    var usuarioId: String?
        set(value) {
            prefs.edit().putString("USUARIO_ID", value).apply()
        }
        get() {
            return prefs.getString("USUARIO_ID", null)
        }

    var correoElectronico :String?
        set(value) {
            prefs.edit().putString("CORREO_ELECTRONICO", value).apply()
        }
        get() {
            return prefs.getString("CORREO_ELECTRONICO", null)
        }

    var contrasena :String?
        set(value) {
            prefs.edit().putString("CONTRASENA", value).apply()
        }
        get() {
            return prefs.getString("CONTRASENA", null)
        }

    var tiendaId: String?
        set(value) {
            prefs.edit().putString("TIENDA_ID", value).apply()
        }
        get() {
            return prefs.getString("TIENDA_ID", null)
        }

}