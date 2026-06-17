package com.example.examenaplicacionesmoviles.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IngresoOperarioScreen(
    onIngresar: (String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }

    // Botón habilitado solo si hay al menos 3 caracteres
    val botonHabilitado = nombre.trim().length >= 3

    Column(
        modifier              = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement   = Arrangement.Center,
        horizontalAlignment   = Alignment.CenterHorizontally
    ) {

        Text(text = "", fontSize = 64.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text       = "Bienvenido a StockPro",
            fontSize   = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign  = TextAlign.Center,
            color      = MaterialTheme.colorScheme.primary
        )

        Text(
            text      = "Sistema de Gestión de Inventario",
            fontSize  = 14.sp,
            color     = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier  = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
            value         = nombre,
            onValueChange = { nombre = it },
            label         = { Text("Nombre del Operario") },
            placeholder   = { Text("Ingresa tu nombre") },
            singleLine    = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction      = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (nombre.isNotEmpty() && nombre.trim().length < 3) {
            Text(
                text     = "El nombre debe tener al menos 3 caracteres",
                color    = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp).align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick  = { onIngresar(nombre.trim()) },
            enabled  = botonHabilitado,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text(text = "Ingresar al Sistema", fontSize = 16.sp)
        }
    }
}

