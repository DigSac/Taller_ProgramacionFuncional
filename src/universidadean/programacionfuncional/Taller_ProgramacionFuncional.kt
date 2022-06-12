package universidadean.programacionfuncional

import ean.collections.IList

// Esta clase guarda la información de un producto de una tienda
data class Producto(val codigo: Int, val nombre: String, val cantidad: Int, val precio: Int)

// Esta clase guarda la información de un departamento del país
data class Departamento(
    val nombre: String,
    val poblacion: Int,
    val superficie: Double,
    val densidad: Double,
    val IDH: Double,
    val añoCreacion: Int)

// Esta clase guarda la información de un municipio del pais
data class Municipio(
    val codigo: Int,
    val nombre: String,
    val departamento: String,
    val poblacionUrbana: Int,
    val poblacionRural: Int,
    val esCapital: Boolean
)

// Esta clase guarda la información de un rectángulo
data class Rectangulo(val base: Double, val altura: Double) {
    // Hallar el área del rectangulo
    fun area(): Double = base * altura
}

// Esta clase guarda la información de un triángulo
data class Triangulo(val id: Int,
                     val lado1: Double,
                     val lado2: Double,
                     val lado3: Double)

//-------------------------------------------------------------------
// Operaciones con la clase Departamento
//-------------------------------------------------------------------

/**
 * Obtener el nombre del departamento más antiguo de toda la lista.
 * Si la lista está vacía, retorne null
 */
fun metodo6(dptos: IList<Departamento>): String? {
    if (dptos.isEmpty)
        return null
    var departamento_antiguo = dptos.minWith(compareBy(Departamento::añoCreacion))
    return departamento_antiguo!!.nombre
}

/**
 * Retorna el  departamento que tiene la superficie más grande
 * pero con una población superior a la población que se pasa
 * como parámetro.
 */
fun metodo7(dptos: IList<Departamento>, poblacion: Int): Departamento? {
    var dptos_poblacion_superior = dptos.filter{ it.poblacion > poblacion }
    val dpto_mayor_superficie = dptos_poblacion_superior.maxWith(compareBy(Departamento::superficie))
    return dpto_mayor_superficie
}

/**
 * Retorne la lista de los nombres de los departamentos creados
 * en el siglo XX y que tenga un IDH entre 0.85 y 0.95
 */
fun metodo8(dptos: IList<Departamento>): IList<String> {
    val dptos_xx_idh = dptos.filter { it.añoCreacion in 1901 .. 2000 && it.IDH in 0.85 .. 0.95}
    return dptos_xx_idh.map { it.nombre }
}

/**
 * Retorne el porcentaje de departamentos de la lista cuya densidad
 * esté por debajo del valor que se pasa como parámetro
 */
fun metodo9(deptos: IList<Departamento>, valor: Double): Double {
    val total = deptos.count()
    val cantidad = deptos.count { it.densidad < valor }
    return 100 * cantidad.toDouble() / total
}

/**
 * Retorne el promedio de superficie de los departamentos de la lista
 * cuya poblacion sea superior a la población del departamento con menor
 * IDH de toda la lista
 */
fun metodo10(deptos: IList<Departamento>): Double {
    var depto_menor_idh = deptos.minWith(compareBy(Departamento::IDH))
    var deptos_pobl_superior = deptos.filter { it.poblacion > depto_menor_idh!!.poblacion }
    var suma = deptos_pobl_superior.map { it.superficie }.sum()
    return suma / deptos_pobl_superior.size
}
//-------------------------------------------------------------------
// Operaciones con la clase Municipio
//-------------------------------------------------------------------

/**
 * Determinar y retornar cuántos municipios de la lista son capitales
 */
fun metodo11(muns: IList<Municipio>): Int {
    return muns.filter { it.esCapital == true }.size
}

/*
 * Determinar el nombre del municipio que no es capital y que pertenece al
 * departamento que se recibe como parámetro y que tiene la población urbana
 * más grande
 */
fun metodo12(m: IList<Municipio>, depto: String): String {
    return m.filter { !it.esCapital && it.departamento == depto }.maxWith(compareBy(Municipio::poblacionUrbana))!!.nombre
}

/**
 * Retornar el promedio de la población total (suma de la población rural y población urbana)
 * de aquellos municipios de la lista que pertenecen al departamento que se pasa
 * como parámetro y cuyo código sea múltiplo de 3 o de 5
 */
fun metodo13(municipios: IList<Municipio>, departamento: String): Double {
    val mun_dep =  municipios.filter { it.departamento == departamento && (it.codigo % 3 == 0 || it.codigo % 5 == 0) }
                    .map { it.poblacionUrbana + it.poblacionRural }
    return mun_dep.sum().toDouble() / mun_dep.count()
}

/**
 * Retorne el nombre del primer municipio que inicia con J en toda la lista
 */
fun metodo14(muns: IList<Municipio>): String {
    return muns.filter { it.nombre.startsWith('J') }[0]!!.nombre
}


/**
 * Retorne cuantos municipios de la lista que tienen un código
 * de 4 dígitos poseen una poblacion rural superior a la población
 * urbana
 */
fun metodo15(muns: IList<Municipio>): Int {
    return muns.filter { it.codigo / 1000 >= 1 && it.poblacionRural > it.poblacionUrbana}.count()
}

//-------------------------------------------------------------------
// Operaciones con la clase Producto
//-------------------------------------------------------------------

/*
 * Obtener el nombre de todos los productos cuyo código es par
 */
fun metodo1(productos: IList<Producto>): IList<String> {
    return productos.filter { it.codigo % 2 == 0 }.map { it.nombre }
}

/**
 * Obtener cuántos productos tienen un precio inferior al producto
 * cuyo código se pasa como parámetro
 */
fun metodo2(productos: IList<Producto>, codProducto: Int): Int {
    val prod = productos.find { it.codigo ==  codProducto}!!
    return productos.filter { it.precio < prod.precio }.count()
}

/**
 * Obtener una lista con los códigos de los productos cuya cantidad sea
 * superior a la cantidad mínima que se pasa como parámetro y cuyo precio
 * esté entre mil y diez mil pesos.
 *
 */
fun metodo3(productos: IList<Producto>, cantidadMinima: Int): IList<Int> {
    return productos.filter { it.cantidad > cantidadMinima && it.precio in 1_000 .. 10_000 }
        .map { it.codigo }
}

/**
 * EL inventario total de la lista es la suma de la multiplicación de la cantidad por el precio
 * de todos y cada uno de los productos de la lista. Este método permite saber si el
 * inventario de la lista es superior al millón de pesos o no.
 */
fun metodo4(prods: IList<Producto>): Boolean {
    return prods.map { it.cantidad * it.precio }.sum() > 1_000_000
}

/**
 * Obtener el promedio de la cantidad de aquellos productos cuyo precio
 * esté por debajo del promedio de precio de todos los productos de la lista
 */
fun metodo5(prods: IList<Producto>): Double {
    val precio_promedio = prods.map { it.precio }!!.sum() / prods.count()
    val prods_debajo_promedio = prods.filter { it.precio < precio_promedio }!!
    return prods_debajo_promedio.map { it.cantidad }.sum().toDouble() / prods_debajo_promedio.count()
}

//-------------------------------------------------------------------
// Operaciones con la clase Rectangulo
//-------------------------------------------------------------------

// ¿Cuántos rectángulos de la lista son cuadrados?
fun metodo16(rects: IList<Rectangulo>): Int {
    return rects.filter { it.base == it.altura }.count()
}

// Promedio del área de los rectangulos cuya base es inferior a su altura
fun metodo17(rects: IList<Rectangulo>): Double {
    val lista_areas = rects.filter { it.base< it.altura }.map { it.area() }
    return lista_areas.sum().toDouble() / lista_areas.count()
}

// Obtener el rectangulo de mayor área de la lista
fun metodo18(rects: IList<Rectangulo>): Rectangulo {
 return rects.maxWith(compareBy { it.area() })!!
}

// Lista con las diagonales de aquellos cuadrados cuya area sea superior al area que se pasa como parámetro
fun metodo19(rects: IList<Rectangulo>, areaMin: Double): IList<Double> {
    // return rects.filter { it.base == it.altura }.filter { it.area() > areaMin }.map { Math.sqrt(2.0) * it.base }
    return rects.filter { it.area() > areaMin }.map { Math.sqrt(Math.pow(it.base, 2.0) + Math.pow(it.altura, 2.0)) }
}

//-------------------------------------------------------------------
// Operaciones con la clase Triangulo
//-------------------------------------------------------------------

/**
 * Un triangulo es rectangulo si un lado (el mas largo) es igual a la raiz cuadrada de
 * la suma de los cuadrados de los otros dos lados
 */
// Retorna true si t es triangulo rectangulo
fun esRectangulo(t: Triangulo): Boolean {
    if (t.lado1 > t.lado2 && t.lado1 > t.lado3)
        return  t.lado1 == Math.sqrt(Math.pow(t.lado2, 2.0) + Math.pow(t.lado3, 2.0))
    if (t.lado2 > t.lado1 && t.lado2 > t.lado3)
        return  t.lado2 == Math.sqrt(Math.pow(t.lado1, 2.0) + Math.pow(t.lado3, 2.0))
    return t.lado3 == Math.sqrt(Math.pow(t.lado1, 2.0) + Math.pow(t.lado2, 2.0))
}

// Hallar el área del triángulo a partir de la fórmula de Herón
fun areaTriangulo(t: Triangulo): Double {
    val s = (t.lado1 + t.lado2 + t.lado3) / 2
    return Math.sqrt(s * (s - t.lado1) * (s - t.lado2) * (s - t.lado3))
}

// Lista de áreas de aquellos triangulos rectangulos de la lista
fun metodo20(triangulos: IList<Triangulo>): IList<Double> {
    return triangulos.filter { esRectangulo(it) }.map { areaTriangulo(it) }
}

// Obtener la lista de identificadores de aquellos triangulos isosceles cuya área no supere 10
fun metodo21(triangulos: IList<Triangulo>): IList<Int> {
    fun esIsosceles(t: Triangulo) = t.lado1 == t.lado2 || t.lado2 == t.lado3 || t.lado1 == t.lado3
    return triangulos.filter { esIsosceles(it) && areaTriangulo(it) <= 10.0 }.map { it.id }
}