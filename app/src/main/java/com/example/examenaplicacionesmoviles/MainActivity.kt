package com.example.examenaplicacionesmoviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.examenaplicacionesmoviles.navegation.StockProNavHost
import com.example.examenaplicacionesmoviles.ui.theme.ExamenaplicacionesmovilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenaplicacionesmovilesTheme {
                StockProNavHost()
            }
        }
    }
}