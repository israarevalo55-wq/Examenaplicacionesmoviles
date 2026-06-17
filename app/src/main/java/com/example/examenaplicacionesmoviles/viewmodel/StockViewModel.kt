package com.example.examenaplicacionesmoviles.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.examenaplicacionesmoviles.model.Producto

class StockViewModel : ViewModel() {

    // Lista reactiva de productos precargados
    val productos = mutableStateListOf(
        Producto(1, "Aceite Motor 10W-40",   "Lubricante para motores a gasolina 1L",   12.50, 15),
        Producto(2, "Filtro de Aire",         "Filtro compatible con vehículos livianos",  8.00,  4),
        Producto(3, "Llanta 195/65 R15",      "Llanta radial para auto sedán",            85.00, 10),
        Producto(4, "Batería 12V 60Ah",       "Batería libre de mantenimiento",           95.00,  2),
        Producto(5, "Pastillas de Freno",     "Kit trasero/delantero cerámicas",          22.00,  0),
        Producto(6, "Anticongelante 1L",      "Refrigerante concentrado color verde",      6.75,  3),
        Producto(7, "Bujía NGK Iridium",      "Set x4 bujías de alto rendimiento",        28.00,  8),
        Producto(8, "Líquido de Frenos DOT4", "Frenos hidráulicos 500ml",                  5.20,  1)
    )

    /** Devuelve un producto por su id. */
    fun obtenerProducto(id: Int): Producto? = productos.find { it.id == id }

    /** Actualiza el stock en la lista reactiva. */
    fun actualizarStock(id: Int, nuevaCantidad: Int) {
        val index = productos.indexOfFirst { it.id == id }
        if (index != -1) {
            val p = productos[index]
            productos[index] = p.copy(stockActual = nuevaCantidad)
        }
    }

    /** Calcula capital total: Σ (precio × stock). */
    fun calcularValorTotalInventario(): Double =
        productos.sumOf { it.precio * it.stockActual }

    /** Productos con stock < 5. */
    fun obtenerProductosEnRiesgo(): List<Producto> =
        productos.filter { it.stockActual < 5 }

    /** Cuántos productos tienen stock == 0. */
    fun contarProductosSinStock(): Int =
        productos.count { it.stockActual == 0 }
}
