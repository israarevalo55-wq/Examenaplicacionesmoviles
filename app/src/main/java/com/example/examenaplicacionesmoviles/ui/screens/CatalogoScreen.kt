package com.example.examenaplicacionesmoviles.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examenaplicacionesmoviles.model.Producto
import com.example.examenaplicacionesmoviles.viewmodel.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    nombreOperario : String,
    viewModel      : StockViewModel,
    onProductoClick: (Int) -> Unit,
    onVerReporte   : () -> Unit
) {
    var verTodo by remember { mutableStateOf(true) }

    val listaFiltrada: List<Producto> = if (verTodo) {
        viewModel.productos.toList()
    } else {
        viewModel.obtenerProductosEnRiesgo()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = "StockPro", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            text     = "Operario: $nombreOperario",
                            fontSize = 13.sp,
                            color    = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onVerReporte) {
                Icon(imageVector = Icons.Default.Assessment, contentDescription = "Ver Reporte")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterButton(
                    text     = "Ver Todo",
                    selected = verTodo,
                    modifier = Modifier.weight(1f),
                    onClick  = { verTodo = true }
                )
                FilterButton(
                    text     = "Stock Crítico",
                    selected = !verTodo,
                    modifier = Modifier.weight(1f),
                    onClick  = { verTodo = false }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text     = "${listaFiltrada.size} producto(s) encontrado(s)",
                fontSize = 12.sp,
                color    = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listaFiltrada, key = { it.id }) { producto ->
                    ProductoCard(
                        producto = producto,
                        onClick  = { onProductoClick(producto.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterButton(
    text    : String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick : () -> Unit
) {
    if (selected) {
        Button(onClick = onClick, modifier = modifier) { Text(text) }
    } else {
        OutlinedButton(onClick = onClick, modifier = modifier) { Text(text) }
    }
}

@Composable
private fun ProductoCard(
    producto: Producto,
    onClick : () -> Unit
) {
    val stockColor = if (producto.stockActual < 5) Color.Red
    else MaterialTheme.colorScheme.onSurface

    Card(
        modifier  = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier              = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = producto.nombre, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text(
                    text  = "Precio: $${String.format("%.2f", producto.precio)}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "Stock", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text       = "${producto.stockActual}",
                    fontWeight = FontWeight.Bold,
                    fontSize   = 22.sp,
                    color      = stockColor
                )
            }
        }
    }
}
