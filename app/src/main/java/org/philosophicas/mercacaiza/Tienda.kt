package org.philosophicas.mercacaiza

import java.util.*

class Tienda : Mappeable {
    var id : String? = UUID.randomUUID().toString()
    var nombre : String? = null
    var palabrasClaves : String? = null
    var direccionFisica : String? = null
    var telefono : String? = null
    var personaResponsable: String? = null
    var cedulaRif : String? = null

}