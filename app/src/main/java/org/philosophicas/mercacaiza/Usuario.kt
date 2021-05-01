package org.philosophicas.mercacaiza

import java.util.*


class Usuario : Mappeable {
    var id : String? = UUID.randomUUID().toString()
    var correo : String? = null
    var contrasena : String? = null

    companion object
    {
        fun get(id: String?, correo:String?, contrasena:String) : Map<String,Any?> {
            val u = Usuario()
            u.id = id
            u.correo = correo
            u.contrasena = contrasena
            return u.map
        }
    }

}




/*
class Usuario {

    var id : String? = UUID.randomUUID().toString()
    var correo : String? = null
    var contrasena : String? = null


    //
    var map : Map<String,String?>
    set(value) {
        id = value["id"]
        correo = value["correo"]
        contrasena = value["contrasena"]
    }
    get() {
        val m = HashMap<String, String?>()
        m["id"] = id
        m["correo"] = correo
        m["contrasena"] = contrasena
        return m
    }

    companion object
    {
        fun get(id: String?, correo:String?, contrasena:String) : Map<String,String?> {
            val u = Usuario()
            u.id = id
            u.correo = correo
            u.contrasena = contrasena
            return u.map
        }
    }

}

 */