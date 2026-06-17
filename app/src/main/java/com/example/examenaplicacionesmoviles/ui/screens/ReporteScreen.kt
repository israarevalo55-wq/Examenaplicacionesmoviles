package com.example.examenaplicacionesmoviles.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun ReporteScreen(
    viewModel       : StockViewModel,
    onVolverCatalogo: () -> Unit
) {
    // Los cálculos los hace el ViewModel, aquí solo los mostramos
    val capitalTotal      = viewModel.calcularValorTotalInventario()
    val productosSinStock = viewModel.contarProductosSinStock()
    val productos         = viewModel.productos.toList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reporte Financiero") },
                navigationIcon = {
                    IconButton(onClick = onVolverCatalogo) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier            = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Tarjeta Capital Total
            item {
                Card(
                    modifier  = Modifier.fillMaxWidth(),
                    colors    = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier            = Modifier.padding(24.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text       = "💰 Capital Invertido Total",
                            fontSize   = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign  = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text       = "$${String.format("%,.2f", capitalTotal)}",
                            fontSize   = 42.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color      = MaterialTheme.colorScheme.primary,
                            textAlign  = TextAlign.Center
                        )
                    }
                }
            }

            // Tarjeta Productos sin stock
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors   = CardDefaults.cardColors(
                        containerColor = if (productosSinStock > 0)
                            MaterialTheme.colorScheme.errorContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier              = Modifier.padding(20.dp).fillMaxWidth(),
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text       = "Productos sin stock",
                                fontWeight = FontWeight.SemiBold,
                                fontSize   = 16.sp
                            )
                            Text(
                                text     = "Stock = 0 unidades",
                                fontSize = 12.sp,
                                color    = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text       = "$productosSinStock",
                            fontSize   = 36.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color      = if (productosSinStock > 0)
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Título detalle
            item {
                Text(
                    text       = "Detalle por Producto",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier   = Modifier.padding(top = 8.dp)
                )
            }

            // Fila por producto
            items(productos) { p ->
                val valorParcial = p.precio * p.stockActual
                Row(
                    modifier              = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(p.nombre, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        Text(
                            text     = "${p.stockActual} u × $${String.format("%.2f", p.precio)}",
                            fontSize = 12.sp,
                            color    = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text       = "$${String.format("%,.2f", valorParcial)}",
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                HorizontalDivider(thickness = 0.5.dp)
            }

            // Botón volver
            item {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick  = onVolverCatalogo,
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) {
                    Text(text = "Volver al Catálogo", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


