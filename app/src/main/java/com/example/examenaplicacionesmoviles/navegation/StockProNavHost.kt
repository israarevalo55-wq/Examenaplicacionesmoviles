package com.example.examenaplicacionesmoviles.navegation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.examenaplicacionesmoviles.ui.screens.CatalogoScreen
import com.example.examenaplicacionesmoviles.ui.screens.EdicionStockScreen
import com.example.examenaplicacionesmoviles.ui.screens.IngresoOperarioScreen
import com.example.examenaplicacionesmoviles.ui.screens.ReporteScreen
import com.example.examenaplicacionesmoviles.viewmodel.StockViewModel

object Routes {
    const val INGRESO       = "ingreso"
    const val CATALOGO      = "catalogo/{nombreOperario}"
    const val EDICION_STOCK = "edicion/{productoId}"
    const val REPORTE       = "reporte"

    fun catalogo(nombre: String) = "catalogo/$nombre"
    fun edicion(id: Int)         = "edicion/$id"
}

@Composable
fun StockProNavHost() {
    val navController = rememberNavController()
    // ViewModel compartido por todas las pantallas
    val viewModel: StockViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.INGRESO) {

        // Pantalla 1 – Ingreso
        composable(Routes.INGRESO) {
            IngresoOperarioScreen(
                onIngresar = { nombre ->
                    navController.navigate(Routes.catalogo(nombre))
                }
            )
        }

        // Pantalla 2 – Catálogo
        composable(
            route     = Routes.CATALOGO,
            arguments = listOf(navArgument("nombreOperario") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombreOperario") ?: ""
            CatalogoScreen(
                nombreOperario  = nombre,
                viewModel       = viewModel,
                onProductoClick = { id -> navController.navigate(Routes.edicion(id)) },
                onVerReporte    = { navController.navigate(Routes.REPORTE) }
            )
        }

        // Pantalla 3 – Edición de Stock
        composable(
            route     = Routes.EDICION_STOCK,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: -1
            EdicionStockScreen(
                productoId = productoId,
                viewModel  = viewModel,
                onGuardar  = { navController.popBackStack() }
            )
        }

        // Pantalla 4 – Reporte
        composable(Routes.REPORTE) {
            ReporteScreen(
                viewModel        = viewModel,
                onVolverCatalogo = { navController.popBackStack() }
            )
        }
    }
}


