package com.example.ventas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    // Variables para acumular el total de ventas y ganancias totales
    private var totalVentas: Float = 0f
    private var gananciasTotales: Float = 0f
    private val historialMovimientos = mutableListOf<Float>() // Historial de movimientos
    private val historialGanancias = mutableListOf<Float>() // Historial de ganancias

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Enlazar el archivo XML

        // Obtener referencias a los elementos de la interfaz
        val precioEditText = findViewById<EditText>(R.id.precioEditText)
        val porcentajeEditText = findViewById<EditText>(R.id.porcentajeEditText)
        val calcularButton = findViewById<Button>(R.id.calcularButton)
        val resultadoTextView = findViewById<TextView>(R.id.resultadoTextView)
        val totalVentasTextView = findViewById<TextView>(R.id.totalVentasTextView)
        val gananciasTotalesTextView = findViewById<TextView>(R.id.gananciasTotalesTextView)

        // Configurar el botón para calcular el precio final
        calcularButton.setOnClickListener {
            val precioOriginal = precioEditText.text.toString().toFloatOrNull()
            val porcentaje = porcentajeEditText.text.toString().toFloatOrNull()

            if (precioOriginal != null && porcentaje != null) {
                val descuento = precioOriginal * (porcentaje / 100)
                val precioFinal = precioOriginal - descuento

                // Actualizar totales
                totalVentas += precioFinal
                gananciasTotales += descuento // Ganancias son el descuento aplicado

                // Agregar el precio final y la ganancia al historial
                historialMovimientos.add(precioFinal)
                historialGanancias.add(descuento) // Agregar ganancia al historial

                // Mostrar resultados
                resultadoTextView.text = "Precio final: $precioFinal"
                totalVentasTextView.text = "Total de Ventas: $totalVentas"
                gananciasTotalesTextView.text = "Ganancias Totales: $gananciasTotales"

                // Limpiar las EditText
                precioEditText.text.clear()
                porcentajeEditText.text.clear()
            } else {
                resultadoTextView.text = "Por favor, ingrese valores válidos."
            }
        }

        // Configurar el botón para deshacer la última acción
        val deshacerButton = findViewById<Button>(R.id.deshacerButton)
        deshacerButton.setOnClickListener {
            if (historialMovimientos.isNotEmpty() && historialGanancias.isNotEmpty()) {
                // Deshacer la última acción
                val ultimoMovimiento = historialMovimientos.removeAt(historialMovimientos.size - 1)
                val ultimaGanancia = historialGanancias.removeAt(historialGanancias.size - 1)

                totalVentas -= ultimoMovimiento // Restar del total de ventas
                gananciasTotales -= ultimaGanancia // Restar de las ganancias totales

                // Actualizar la visualización
                totalVentasTextView.text = "Total de Ventas: $totalVentas"
                gananciasTotalesTextView.text = "Ganancias Totales: $gananciasTotales"
                resultadoTextView.text = "Último movimiento deshecho: $ultimoMovimiento, Ganancia deshecha: $ultimaGanancia"
            } else {
                resultadoTextView.text = "No hay movimientos para deshacer."
            }
        }

        // Configurar el botón para borrar todo el historial
        val borrarHistorialButton = findViewById<Button>(R.id.borrarHistorialButton)
        borrarHistorialButton.setOnClickListener {
            historialMovimientos.clear() // Limpiar el historial de movimientos
            historialGanancias.clear() // Limpiar el historial de ganancias
            totalVentas = 0f // Reiniciar totales
            gananciasTotales = 0f
            totalVentasTextView.text = "Total de Ventas: $totalVentas"
            gananciasTotalesTextView.text = "Ganancias Totales: $gananciasTotales"
            resultadoTextView.text = "Historial borrado."
        }
    }
}
