package com.example.examenaplicacionesmoviles.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examenaplicacionesmoviles.viewmodel.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EdicionStockScreen(
    productoId: Int,
    viewModel : StockViewModel,
    onGuardar : () -> Unit
) {
    val producto = viewModel.obtenerProducto(productoId)

    var stockLocal by remember(producto?.stockActual) {
        mutableStateOf(producto?.stockActual ?: 0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title  = { Text("Editar Stock") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->

        if (producto == null) {
            Box(
                modifier         = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) { Text("Producto no encontrado.") }
            return@Scaffold
        }

        Column(
            modifier              = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement   = Arrangement.spacedBy(16.dp),
            horizontalAlignment   = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors   = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier            = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text       = producto.nombre,
                        fontSize   = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign  = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text      = producto.descripcion,
                        fontSize  = 14.sp,
                        color     = MaterialTheme.colorScheme.onSecondaryContainer,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text     = "Precio unitario: $${String.format("%.2f", producto.precio)}",
                        fontSize = 13.sp,
                        color    = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Stock Actual", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // Botón Restar — deshabilitado si stock es 0
                FilledIconButton(
                    onClick  = { if (stockLocal > 0) stockLocal-- },
                    enabled  = stockLocal > 0,
                    modifier = Modifier.size(56.dp),
                    colors   = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Icon(
                        imageVector        = Icons.Default.Remove,
                        contentDescription = "Restar stock",
                        modifier           = Modifier.size(28.dp)
                    )
                }

                Text(
                    text       = "$stockLocal",
                    fontSize   = 56.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color      = if (stockLocal < 5) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary
                )

                // Botón Sumar
                FilledIconButton(
                    onClick  = { stockLocal++ },
                    modifier = Modifier.size(56.dp),
                    colors   = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Icon(
                        imageVector        = Icons.Default.Add,
                        contentDescription = "Sumar stock",
                        modifier           = Modifier.size(28.dp)
                    )
                }
            }

            if (stockLocal == 0) {
                Text(
                    text     = "⚠ Sin stock disponible",
                    color    = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick  = {
                    viewModel.actualizarStock(productoId, stockLocal)
                    onGuardar()
                },
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text(text = "Guardar y Volver", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

