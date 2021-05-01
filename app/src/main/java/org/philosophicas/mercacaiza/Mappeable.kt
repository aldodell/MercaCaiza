package org.philosophicas.mercacaiza


interface Mappeable {


    var map: Map<String, Any?>
        get() {
            val m = HashMap<String, Any?>()
            this::class.java.declaredFields.forEach { f ->
                f.isAccessible = true
                val z = f.get(this)
                m[f.name] = z
            }
            return m
        }
        set(value) {
            this::class.java.declaredFields.forEach { f ->
                f.set(f.name, value[f.name])
            }
        }
}

/*
 Int::class.java -> {
                        m[f.name] = f.getInt(this)
                    }

                    Float::class.java -> {
                        m[f.name] = f.getFloat(this)
                    }

                    String::class.java -> {
                        m[f.name] = f.get
                    }
 */